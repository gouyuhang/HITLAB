def generate_trainfile():
    lineInt = 0
    read_file = open('../iofile/199801_seg&pos.txt', 'r', encoding='gbk')
    write_file = open('../iofile/train.txt', 'w', encoding='gbk')
    for line in read_file:
        lineInt += 1
        if lineInt % 10 != 0:
            write_file.write(line)


def generate_standardfile():
    lineInt = 0
    read_file = open('../iofile/199801_seg&pos.txt', 'r', encoding='gbk')
    write_file = open('../iofile/standard.txt', 'w', encoding='gbk')
    for line in read_file:
        lineInt += 1
        if lineInt % 10 == 0:
            write_file.write(line)


def generate_testfile():
    lineInt = 0
    read_file = open('../iofile/199801_sent.txt', 'r', encoding='gbk')
    write_file = open('../iofile/test.txt', 'w', encoding='gbk')
    for line in read_file:
        lineInt += 1
        if lineInt % 10 == 0:
            write_file.write(line)
