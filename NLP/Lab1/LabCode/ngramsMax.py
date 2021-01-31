# coding=gbk
import math

from LabCode import HMM, pre_line


class MaxProbCut:
    def __init__(self):
        self.word_dict = {}  # ��¼����,1-gram
        self.word_dict_count = {}  # ��¼��Ƶ,1-gram
        self.trans_dict = {}  # ��¼����,2-gram
        self.trans_dict_count = {}  # ��¼��Ƶ,2-gram
        self.max_wordlen = 0  # �ʵ������
        self.all_freq = 0  # ���дʵĴ�Ƶ�ܺ�,1-gram
        word_count_path = "../iofile/word_dict.model"
        word_trans_path = '../iofile/trans_dict.model'
        self.init(word_count_path, word_trans_path)

    # ���شʵ�
    def init(self, word_count_path, word_trans_path):
        self.word_dict_count = self.load_model(word_count_path)
        self.all_freq = sum(self.word_dict_count.values())  # ���дʵĴ�Ƶ
        self.max_wordlen = max(len(key) for key in self.word_dict_count.keys())
        for key in self.word_dict_count:
            self.word_dict[key] = math.log(self.word_dict_count[key] / self.all_freq)
        # ����ת�Ƹ���
        Trans_dict = self.load_model(word_trans_path)
        for pre_word, post_info in Trans_dict.items():
            for post_word, count in post_info.items():
                word_pair = pre_word + ' ' + post_word
                self.trans_dict_count[word_pair] = float(count)
                if pre_word in self.word_dict_count.keys():
                    self.trans_dict[key] = math.log(count / self.word_dict_count[pre_word])  # ȡ��Ȼ��������һ��
                else:
                    self.trans_dict[key] = self.word_dict[post_word]

    # ����Ԥѵ��ģ��
    def load_model(self, model_path):
        f = open(model_path, 'r',encoding='gbk')
        a = f.read()
        word_dict = eval(a)
        f.close()
        return word_dict

    # ����δ���ֵĴʵĸ���,����beautiful data����ķ������㣬ƽ���㷨
    def get_unknow_word_prob(self, word):
        return math.log(1.0 / (self.all_freq ** len(word)))

    # ��ȡ��ѡ�ʵĸ���
    def get_word_prob(self, word):
        if word in self.word_dict.keys():  # ����ֵ���������
            prob = self.word_dict[word]
        else:
            prob = self.get_unknow_word_prob(word)
        return prob

    # ��ȡת�Ƹ���
    def get_word_trans_prob(self, pre_word, post_word):
        trans_word = pre_word + " " + post_word

        if trans_word in self.trans_dict_count.keys():
            trans_prob = math.log(self.trans_dict_count[trans_word] / self.word_dict_count[pre_word])
        else:
            trans_prob = self.get_word_prob(post_word)
        return trans_prob

    # Ѱ��node�����ǰ���ڵ㣬����ΪѰ�����п��ܵ�ǰ��Ƭ��
    def get_best_pre_node(self, sentence, node, node_state_list):
        # ���node�����ʳ�С��ȡ��Ƭ�γ�����node�ĳ���Ϊ��
        max_seg_length = min([node, self.max_wordlen])
        pre_node_list = []  # ǰ���ڵ��б�

        # ������е�ǰ��Ƭ�Σ�����¼�ۼӸ���
        for segment_length in range(1, max_seg_length + 1):
            segment_start_node = node - segment_length
            segment = sentence[segment_start_node:node]  # ��ȡƬ��
            pre_node = segment_start_node  # ȡ��Ƭ�Σ����¼��Ӧ��ǰ���ڵ�
            if pre_node == 0:
                # ���ǰ��Ƭ�ο�ʼ�ڵ������еĿ�ʼ�ڵ㣬
                # �����Ϊ<S>ת�Ƶ���ǰ�ʵĸ���
                segment_prob = self.get_word_trans_prob("<BEG>", segment)
            else:  # ����������п�ʼ�ڵ㣬���ն�Ԫ���ʼ���
                # ���ǰ��Ƭ�ε�ǰһ����
                pre_pre_node = node_state_list[pre_node]["pre_node"]
                pre_pre_word = sentence[pre_pre_node:pre_node]
                segment_prob = self.get_word_trans_prob(pre_pre_word, segment)

            pre_node_prob_sum = node_state_list[pre_node]["prob_sum"]  # ǰ���ڵ�ĸ��ʵ��ۼ�ֵ
            # ��ǰnodeһ����ѡ���ۼӸ���ֵ
            candidate_prob_sum = pre_node_prob_sum + segment_prob
            pre_node_list.append((pre_node, candidate_prob_sum))

        # �ҵ����ĺ�ѡ����ֵ
        (best_pre_node, best_prob_sum) = max(pre_node_list, key=lambda d: d[1])
        return best_pre_node, best_prob_sum

    # �д�������
    def cut_main(self, sentence):
        sentence = sentence.strip()
        # ��ʼ��
        node_state_list = []  # ��¼�ڵ�����ǰ����index����λ����Ϣ
        # ��ʼ�ڵ㣬Ҳ����0�ڵ���Ϣ
        ini_state = {}
        ini_state["pre_node"] = -1  # ǰһ���ڵ�
        ini_state["prob_sum"] = 0  # ��ǰ�ĸ����ܺ�
        node_state_list.append(ini_state)
        # �ַ�������Ϊ2Ԫ���ʣ� P(a b c) = P(a|<S>)P(b|a)P(c|b)
        # ����ڵ�Ѱ�����ǰ���ڵ�
        for node in range(1, len(sentence) + 1):
            # Ѱ�����ǰ��������¼��ǰ���ĸ����ۼ�ֵ
            (best_pre_node, best_prob_sum) = self.get_best_pre_node(sentence, node, node_state_list)
            # ��ӵ�����
            cur_node = {}
            cur_node["pre_node"] = best_pre_node
            cur_node["prob_sum"] = best_prob_sum
            node_state_list.append(cur_node)

            # print "cur node list",node_state_list

        # step 2, �������·��,�Ӻ�ǰ
        best_path = []
        node = len(sentence)  # ���һ����
        best_path.append(node)
        while True:
            pre_node = node_state_list[node]["pre_node"]
            if pre_node == -1:
                break
            node = pre_node
            best_path.append(node)
        best_path.reverse()

        # step 3, �����з�
        word_list = []
        for i in range(len(best_path) - 1):
            left = best_path[i]
            right = best_path[i + 1]
            word = sentence[left:right]
            word_list.append(word)

        return word_list

    # ���Խӿ�
    def cut(self, sentence):
        return self.cut_main(sentence)

    def test(self):
        cuter = MaxProbCut()
        with open('../iofile/test.txt', 'r', encoding='gbk') as f:
            lines = f.readlines()
        with open('../iofile/Bigrams_NoHMM.txt', 'w', encoding='gbk') as mwf_file:
            lineInt = 0
            for line in lines:
                segline = ''
                seg_list = cuter.cut(line)
                for i in range(len(seg_list)):
                    segline += seg_list[i] + '/ '
                mwf_file.write(segline + '\n')


    def testHMM(self):
        cuter = MaxProbCut()
        HMM.tag_txt('../iofile/train.txt')
        with open('../iofile/test.txt', 'r', encoding='gbk') as f:
            lines = f.readlines()
        with open('../iofile/Bigrams_HMM.txt', 'w', encoding='gbk') as mwf_file:
            lineInt = 0
            for line in lines:
                lineInt += 1
                segline = ''
                seg_list = cuter.cut(line)
                for i in range(len(seg_list)):
                    segline += seg_list[i] + '/ '
                seg_line1 = HMM.cutoovline(segline) if segline else ''
                mwf_file.write(seg_line1+ '\n')
