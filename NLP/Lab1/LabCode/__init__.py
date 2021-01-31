# coding=gbk
import time

from LabCode import dic, FMM_BMM, HMM
from LabCode import generate
from LabCode import Unigram
from LabCode.FMM_BMM import pre_line
from LabCode.train_2grams import TrainNgram
from LabCode.ngramsMax import MaxProbCut


def selfTestFMM_BMM():
    generate.generate_testfile()
    generate.generate_trainfile()
    generate.generate_standardfile()
    dic.gene_dic("../iofile/train.txt", '../iofile/dic.txt')
    FMM_BMM.FMM("../iofile/test.txt", '../iofile/dic.txt', '../iofile/selfseg_FMM.txt', True)
    FMM_BMM.BMM("../iofile/test.txt", '../iofile/dic.txt', '../iofile/selfseg_BMM.txt', True)
    FMM_BMM.score("../iofile/standard.txt", '../iofile/selfseg_FMM.txt', 'selfFMM')
    FMM_BMM.score("../iofile/standard.txt", '../iofile/selfseg_BMM.txt', 'selfBMM')


def fullTestFMM_BMM():
    dic.gene_dic('../iofile/199801_seg&pos.txt', '../iofile/dic.txt')
    FMM_BMM.FMM('../iofile/199801_sent.txt', '../iofile/dic.txt', '../iofile/seg_FMM.txt', True)
    FMM_BMM.BMM('../iofile/199801_sent.txt', '../iofile/dic.txt', '../iofile/seg_BMM.txt', True)
    FMM_BMM.score("../iofile/199801_seg&pos.txt", '../iofile/seg_FMM.txt', 'FMM')
    FMM_BMM.score("../iofile/199801_seg&pos.txt", '../iofile/seg_BMM.txt', 'BMM')


def timeCostTest():
    lineInt = 0
    read_file = open('../iofile/199801_sent.txt', 'r', encoding='gbk')
    write_file = open('../iofile/test.txt', 'w', encoding='gbk')
    for line in read_file:
        lineInt += 1
        if lineInt <= 50:
            write_file.write(line)
    generate.generate_trainfile()
    dic.gene_dic("../iofile/train.txt", '../iofile/dic.txt')
    a = time.time()
    FMM_BMM.FMM("../iofile/test.txt", '../iofile/dic.txt', '../iofile/selfseg_FMM.txt', False)
    b = time.time()
    FMM_BMM.FMM("../iofile/test.txt", '../iofile/dic.txt', '../iofile/selfseg_FMM.txt', True)
    c = time.time()
    time_file = open('../iofile/TimeCost.txt', 'w', encoding='gbk')
    time_file.write('优化前' + str((b - a)) + 's' + '\n' + '优化后' + str((c - b)) + 's' + '\n')


def testUnigram():
    generate.generate_testfile()
    generate.generate_trainfile()
    generate.generate_standardfile()
    Unigram.cut_UniDAG_NoHMM()
    Unigram.cut_UniDAG_HMM()
    FMM_BMM.score("../iofile/standard.txt", '../iofile/Unigram_NoHMM.txt', 'Unigram_NoHMM')
    FMM_BMM.score("../iofile/standard.txt", '../iofile/Unigram_HMM.txt', 'Unigram_HMM')


def testBigram():
    generate.generate_testfile()
    generate.generate_trainfile()
    generate.generate_standardfile()
    train_data_path = '../iofile/train.txt'
    wordict_path = '../iofile/word_dict.model'
    transdict_path = '../iofile/trans_dict.model'
    trainer = TrainNgram()
    trainer.train(train_data_path, wordict_path, transdict_path)
    Test = MaxProbCut()
    Test.test()
    Test.testHMM()
    FMM_BMM.score("../iofile/standard.txt", '../iofile/Bigrams_NoHMM.txt', 'Bigrams_NoHMM')
    FMM_BMM.score("../iofile/standard.txt", '../iofile/Bigrams_HMM.txt', 'Bigrams_HMM')


def FinalPerformanceTest():
    train_data_path = '../iofile/199801_seg&pos.txt'
    wordict_path = '../iofile/word_dict.model'
    transdict_path = '../iofile/trans_dict.model'
    trainer = TrainNgram()
    trainer.train(train_data_path, wordict_path, transdict_path)
    cuter = MaxProbCut()
    HMM.tag_txt('../iofile/199801_seg&pos.txt')
    with open('../iofile/test_sent.txt', 'r', encoding='gbk') as f:
        lines = f.readlines()
    with open('../iofile/seg_LM.txt', 'w', encoding='gbk') as mwf_file:
        lineInt = 0
        for line in lines:
            lineInt += 1
            segline = ''
            seg_list = cuter.cut(line)
            for i in range(len(seg_list)):
                segline += seg_list[i] + '/ '
            seg_line1 = HMM.cutoovline(segline) if segline else ''
            mwf_file.write(pre_line(seg_line1) + '\n')


if __name__ == '__main__':
    selfTestFMM_BMM()  # 自己生成训练集测试集测试FMM&BMM(已经优化过了)
    #fullTestFMM_BMM()#全部测试FMM&BMM 199801seg&pos训练集 199801_sent测试集
    #timeCostTest()#优化前后时间差距
    #testUnigram()#测试Unigram最大概率分词包含是否使用未登录词识别
    #testBigram()#测Bigrams最大概率分词包含是否使用未登录词识别
    #FinalPerformanceTest()#最终性能测试(读取../iofile/test_sent.txt,输出到../iofile/seg_LM.txt)
