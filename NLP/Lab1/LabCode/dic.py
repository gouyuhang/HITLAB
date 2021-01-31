def gene_dic(train_path, dic_path):
    max_len, word_set = 0, set()  # 保存最大词长，保存所有的词，要求具有唯一性且可排序
    seg_file = open(train_path, 'r', encoding='gbk')
    dic_file = open(dic_path, 'w', encoding='gbk')
    lineInt = 0
    for line in seg_file:
        list1 = line.split()
        for word in list1:
            if '/m' in word:
                continue
            word = word[1 if word[0] == '[' else 0:word.index("/")]  # 去掉两个空格之间的非词字符
            word_set.add(word)  # 将词加入词典
            max_len = len(word) if len(word) > max_len else max_len  # 更新最大词长
    word_list = list(word_set)
    word_list.sort()  # 对此列表进行排序
    dic_file.write('\n'.join(word_list))  # 一个词一行
    return word_list, max_len
