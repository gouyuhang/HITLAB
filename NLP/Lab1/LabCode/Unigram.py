# coding=gbk
from math import log
from LabCode import HMM


class UniDAG_NoHMM:
    @staticmethod
    def gene_uni_dic(trainpath):
        Word_Freq = {}
        totalCount = 0
        file = open(trainpath, 'r', encoding='gbk')
        uni_file = open('../iofile/uni_dic.txt', 'w', encoding='gbk')
        for line in file:
            list1 = line.split()
            for word in list1:
                totalCount += 1
                word = word[1 if word[0] == '[' else 0:word.index("/")]
                if word in Word_Freq:
                    Word_Freq[word] = Word_Freq[word] + 1
                else:
                    Word_Freq[word] = 1
        Word_Freq = sorted(Word_Freq.items(), key=lambda x: x[0])
        for i in range(len(Word_Freq)):
            uni_file.write(Word_Freq[i][0] + " " + str(Word_Freq[i][1]) + '\n')
        return Word_Freq, totalCount


def gen_pre_dict(filename):
    lfreq = {}
    ltotal = 0
    with open(filename, encoding='gbk') as fp:
        line = fp.readline()
        while len(line) > 0:
            word, freq = line.split()
            freq = int(freq)
            lfreq[word] = freq
            ltotal += freq
            for ch in range(len(word)):
                wfrag = word[:ch + 1]
                if wfrag not in lfreq:
                    lfreq[wfrag] = 0
            line = fp.readline()
    return lfreq, ltotal


def get_DAG(sentence, lfreq):
    DAG = {}
    N = len(sentence)
    for k in range(N):
        tmplist = []
        i = k
        frag = sentence[k]
        while i < N and frag in lfreq:
            if lfreq[frag] > 0:
                tmplist.append(i)
            i += 1
            frag = sentence[k:i + 1]
        if not tmplist:
            tmplist.append(k)
        DAG[k] = tmplist
    return DAG


def calc(sentence, DAG, lfreq, ltotal):
    N = len(sentence)
    route = {N: (0, 0)}
    logtotal = log(ltotal)
    for idx in range(N - 1, -1, -1):
        route[idx] = max((log(lfreq.get(sentence[idx:x + 1]) or 1) - logtotal + route[x + 1][0], x) for x in DAG[idx])
    return route


def cut_UniDAG_NoHMM():
    Uni = UniDAG_NoHMM()
    Uni.gene_uni_dic("../iofile/train.txt")
    lfreg, ltotal = gen_pre_dict('../iofile/uni_dic.txt')
    with open('../iofile/test.txt', 'r', encoding='gbk') as f:
        lines = f.readlines()
    with open('../iofile/Unigram_NoHMM.txt', 'w', encoding='gbk') as mwf_file:
        lineInt = 0
        for line in lines:
            lineInt += 1
            if line[len(line) - 1] == '\n':
                line = line[:len(line) - 1]
            DAG = get_DAG(line, lfreg)
            line_route = calc(line, DAG, lfreg, ltotal)
            old_start = 0
            seg_line = ''
            while old_start < len(line):
                new_start = line_route[old_start][1] + 1
                seg_line += line[old_start:new_start] + '/ '
                old_start = new_start
            mwf_file.write(seg_line + '\n')


def cut_UniDAG_HMM():
    Uni = UniDAG_NoHMM()
    HMM.tag_txt('../iofile/train.txt')
    Uni.gene_uni_dic("../iofile/train.txt")
    lfreg, ltotal = gen_pre_dict('../iofile/uni_dic.txt')
    with open('../iofile/test.txt', 'r', encoding='gbk') as f:
        lines = f.readlines()
    with open('../iofile/Unigram_HMM.txt', 'w', encoding='gbk') as mwf_file:
        lineInt = 0
        for line in lines:
            lineInt += 1
            if line[len(line) - 1] == '\n':
                line = line[:len(line) - 1]
            DAG = get_DAG(line, lfreg)
            line_route = calc(line, DAG, lfreg, ltotal)
            old_start = 0
            seg_line = ''
            while old_start < len(line):
                new_start = line_route[old_start][1] + 1
                seg_line += line[old_start:new_start] + '/ '
                old_start = new_start
            seg_line1 = HMM.cutoovline(seg_line) if seg_line else ''
            mwf_file.write(seg_line1 + '\n')
