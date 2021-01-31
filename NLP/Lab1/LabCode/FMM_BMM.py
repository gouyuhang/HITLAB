# coding=gbk
from LabCode.trie_tree import Trie


def pre_line(line):
    punctuation = '-./'
    buffer, result = '', ''
    word_list = line.split('/ ')
    word_list = word_list[:len(word_list) - 1]
    for i in range(len(word_list)):
        if word_list[i].isascii() or word_list[i] in punctuation:  # 若是字母、数字或者英文标点
            buffer += word_list[i]
            if i + 1 == len(word_list):
                result += buffer + '/ '
        else:
            if buffer:
                result += buffer + '/ '
                buffer = ''
            result += word_list[i] + '/ '
    return result


def FMM(testpath, dicpath, outpath,improve):
    total_time = 0
    dic_file = open(outpath, 'w', encoding='gbk')
    file = open(dicpath, 'r', encoding='gbk')
    try:
        b = file.read()
    finally:
        file.close()
    dic = b.split('\n')
    maxLen = 0
    tree = Trie()
    for word in dic:
        tree.insert(word)
        if len(word) > maxLen:
            maxLen = len(word)
    seg_file = open(testpath, 'r', encoding='gbk')
    lineInt = 0
    for text in seg_file:
        lineInt += 1
        segLine = ''
        if text[len(text) - 1] == '\n':
            text = text[:len(text) - 1]
        while len(text) > 0:
            length = maxLen
            if len(text) < maxLen:
                length = len(text)
            tryWord = text[0:length]
            if improve:
                while not tree.search(tryWord):
                    if len(tryWord) == 1:
                        break
                    tryWord = tryWord[0:len(tryWord) - 1]
            else:
                while tryWord not in dic:
                    if len(tryWord) == 1:
                        break
                    tryWord = tryWord[0:len(tryWord) - 1]
            segLine += tryWord + '/ '
            text = text[len(tryWord):]
        dic_file.write(pre_line(segLine) + '\n')


def BMM(testpath, dicpath, outpath,improve):
    total_time = 0
    file = open(dicpath, 'r', encoding='gbk')
    dic_file = open(outpath, 'w', encoding='gbk')
    tree = Trie()
    try:
        b = file.read()
    finally:
        file.close()
    dic = b.split('\n')
    maxLen = 0
    for word in dic:
        tree.insert(word)
        if len(word) > maxLen:
            maxLen = len(word)
    seg_file = open(testpath, 'r', encoding='gbk')
    lineInt = 0
    for text in seg_file:
        lineInt += 1
        segList = []
        if text[len(text) - 1] == '\n':
            text = text[:len(text) - 1]
        while len(text) > 0:
            length = maxLen
            if len(text) < maxLen:
                length = len(text)
            tryWord = text[len(text) - length:]
            if improve:
                while not tree.search(tryWord):
                    if len(tryWord) == 1:
                        break
                    tryWord = tryWord[1:]
            else:
                while tryWord not in dic:
                    if len(tryWord) == 1:
                        break
                    tryWord = tryWord[1:]
            segList.insert(0, tryWord + '/ ')
            text = text[:len(text) - len(tryWord)]
        dic_file.write(pre_line(''.join(segList)) + '\n')


def encode(aList, bList):
    cList = []
    cFlag = 1
    dList = []
    dFlag = 1
    for i in range(len(aList)):
        cList.append(cFlag * 31 + len(aList[i]))
        cFlag += len(aList[i])
    for j in range(len(bList)):
        dList.append(dFlag * 31 + len(bList[j]))
        dFlag += len(bList[j])
    return cList, dList


def score(standard_path, result_path, Method):
    standard_result = open(standard_path, 'r', encoding='gbk')
    my_result = open(result_path, 'r', encoding='gbk')
    standard_result_list = []
    my_result_list = []
    standard_total_count = 0
    my_total_count = 0
    lineInt = 0
    right_seg_words = 0
    for line1, line2 in zip(standard_result, my_result):
        standard_result_list.clear()
        my_result_list.clear()
        for word in line1.split():
            word = word[1 if word[0] == '[' else 0:word.index("/")]  # 去掉两个空格之间的非词字符
            standard_result_list.append(word)  # 将词加入词典
        for WORD in line2.split('/ '):
            my_result_list.append(WORD)
        standard_total_count += len(standard_result_list)
        if my_result_list[len(my_result_list) - 1] == '\n':
            my_result_list = my_result_list[:len(my_result_list) - 1]
        my_total_count += len(my_result_list)
        cList, dList = encode(standard_result_list, my_result_list)
        for i in range(len(cList)):
            for j in range(len(dList)):
                if cList[i] == dList[j]:
                    right_seg_words += 1
                    break
        cList.clear()
        dList.clear()
    r = float(right_seg_words) / float(standard_total_count)
    p = float(right_seg_words) / float(my_total_count)
    f1 = (2.0 * p * r) / (p + r)
    score_file = open('../iofile/score_' + str(Method) + '.txt', 'w', encoding='gbk')
    score_file.write("准确率:" + str(p) + '\n' + "召回率:" + str(r) + '\n' + "F1值:" + str(f1) + '\n')
