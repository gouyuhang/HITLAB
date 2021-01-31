# coding=gbk
from math import log

PrevStatus = {
    'B': 'ES',
    'M': 'MB',
    'S': 'SE',
    'E': 'BM'
}

MIN_FLOAT = -3.14e100
start_P = {}
trans_P = {}
emit_P = {}
States = ['B', 'M', 'E', 'S']
State_Count = {}  # ����״̬���ִ���
Word_Dic = set()
Word_Count = 0


def init():
    for state in States:
        start_P[state] = 0.0
        State_Count[state] = 0
        emit_P[state], trans_P[state] = {}, {}
        for state_1 in States:
            trans_P[state][state_1] = 0.0


def tag_line(line):
    global Word_Count
    line_word, line_tag = [], []  # ����ÿһ�е����е��ֺ�״̬[B,M,E,S]
    for word in line.split():
        word = word[1 if word[0] == '[' else 0:word.index('/')]  # ȡ��һ����
        line_word.extend(list(word))  # ����һ���ʵ�ÿ���ּӵ����е����б���
        Word_Dic.add(word)  # ���ôʱ�����Word_Dic��
        Word_Count += 1
        if len(word) == 1:
            line_tag.append('S')
            start_P['S'] += 1
        else:
            line_tag.append('B')
            line_tag.extend(['M'] * (len(word) - 2))
            line_tag.append('E')
            start_P['B'] += 1
    return line_word, line_tag


def tag_txt(train_txt):
    init()
    with open(train_txt, 'r', encoding='gbk') as txt_f:
        lines = txt_f.readlines()
    for line in lines:
        if line == '\n':
            continue
        line_word, line_tag = tag_line(line)  # �õ�һ�б�ע
        for i in range(len(line_tag)):
            State_Count[line_tag[i]] += 1  # ����״̬�ܳ��ִ���
            emit_P[line_tag[i]][line_word[i]] = emit_P[line_tag[i]].get(line_word[i], 0) + 1
            if i > 0:
                trans_P[line_tag[i - 1]][line_tag[i]] += 1  # ת�Ƹ��ʱ仯
    for state in States:
        start_P[state] = MIN_FLOAT if start_P[state] == 0 else log(start_P[state] / Word_Count)
        for state_1 in States:  # ����״̬ת�Ƹ���
            trans_P[state][state_1] = MIN_FLOAT if trans_P[state][state_1] == 0 else log(
                trans_P[state][state_1] / State_Count[state])
        for word in emit_P[state].keys():  # ���㷢�����
            emit_P[state][word] = log(emit_P[state][word] / State_Count[state])


def viterbi(obs, states, start_p, trans_p, emit_p):
    V = [{}]  # tabular
    path = {}
    for y in states:  # init
        V[0][y] = start_p[y] + emit_p[y].get(obs[0], MIN_FLOAT)
        path[y] = [y]
    for t in range(1, len(obs)):
        V.append({})
        newpath = {}
        for y in states:
            em_p = emit_p[y].get(obs[t], MIN_FLOAT)
            (prob, state) = max(
                [(V[t - 1][y0] + trans_p[y0].get(y, MIN_FLOAT) + em_p, y0) for y0 in PrevStatus[y]])
            V[t][y] = prob
            newpath[y] = path[state] + [y]
        path = newpath

    (prob, state) = max((V[len(obs) - 1][y], y) for y in 'ES')

    return prob, path[state]


def cutoovword(word):
    prob, pos_list = viterbi(word, 'BMES', start_P, trans_P, emit_P)
    begin, nexti = 0, 0
    recon = ''
    # print pos_list, sentence
    for i, char in enumerate(word):
        pos = pos_list[i]
        if pos == 'B':
            begin = i
        elif pos == 'E':
            recon += word[begin:i + 1] + '/ '
            nexti = i + 1
        elif pos == 'S':
            recon += char + '/ '
            nexti = i + 1
    if nexti < len(word):
        recon += word[nexti:] + '/ '
    return recon


def cutoovline(line):
    word_list = line[:len(line) - 2].split('/ ')  # �õ����еĴ����б�
    seg_line, to_seg_word = '', ''  # ���������ĵ��֣����ڽ�һ��ʹ��HMM����δ��¼��ʶ��
    for idx in range(len(word_list)):
        if len(word_list[idx]) == 1:  # ����һ�����֣�������뵽������������
            if word_list[idx] in Word_Dic:  # �õ����Ǵʵ��еĴ�
                if to_seg_word:  # �ж��Ƿ�ô�ǰ��Ϊ����
                    seg_line += cutoovword(to_seg_word)
                    to_seg_word = ''
                seg_line += word_list[idx] + '/ '
            else:  # �õ��ֲ��Ǵʵ��еĴ�
                to_seg_word += word_list[idx]
                if idx + 1 == len(word_list):
                    seg_line += cutoovword(to_seg_word)
        else:  # �����ǵ������
            if to_seg_word:  # �ж��Ƿ�ô�ǰ��Ϊ����
                seg_line += cutoovword(to_seg_word)
                to_seg_word = ''
            seg_line += word_list[idx] + '/ '
    return seg_line
