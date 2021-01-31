# coding=gbk
class TrainNgram():
    def __init__(self):
        self.word_dict = {}  # ����Ƶ�δʵ�
        self.transdict = {}  # ÿ���ʺ�Ӵʵĳ��ָ���

    '''ѵ��ngram����'''

    def train(self, train_data_path, wordict_path, transdict_path):
        self.transdict[u'<BEG>'] = {}
        self.word_dict['<BEG>'] = 0

        for sentence in open(train_data_path,encoding='gbk'):
            self.word_dict['<BEG>'] += 1
            sentence = sentence.strip()
            sentence = sentence.split(' ')
            sentence_list = []
            # ['���£�����', '', '���磴ʱ', '', '��', '', 'μ����', '', '��ƽ��ׯ����վ', '', '��'], �õ�ÿ���ʳ��ֵĸ���
            for pos, words in enumerate(sentence):
                if words != '':
                    words = words[1 if words[0] == '[' else 0:words.index("/")]
                    sentence_list.append(words)
            # ['���£�����', '���磴ʱ', 'μ����', '��ƽ��ׯ����վ']
            for pos, words in enumerate(sentence_list):
                if words not in self.word_dict.keys():
                    self.word_dict[words] = 1
                else:
                    self.word_dict[words] += 1
                # ��Ƶͳ��
                # �õ�ÿ���ʺ�Ӵʳ��ֵĸ�����bigram <word1, word2>
                words1, words2 = '', ''
                # ����Ǿ��ף���Ϊ<BEG��word>
                if pos == 0:
                    words1, words2 = u'<BEG>', words
                # ����Ǿ�β����Ϊ<word, END>
                elif pos == len(sentence_list) - 1:
                    words1, words2 = words, u'<END>'
                # ����Ǿ��ף���β����Ϊ <word1, word2>
                else:
                    words1, words2 = words, sentence_list[pos + 1]
                # ͳ�Ƶ�ǰ�ʺ�Ӵ�����ֵĴ�����{���ҡ���{���ǡ���1�� ��������2}}
                if words not in self.transdict.keys():
                    self.transdict[words1] = {}
                if words2 not in self.transdict[words1]:
                    self.transdict[words1][words2] = 1
                else:
                    self.transdict[words1][words2] += 1

        self.save_model(self.word_dict, wordict_path)
        self.save_model(self.transdict, transdict_path)

    '''����ģ��'''

    def save_model(self, word_dict, model_path):
        f = open(model_path, 'w',encoding='gbk')
        f.write(str(word_dict))
        f.close()

