import numpy as np

from labcode import kmeans, kmeans_test, GMM_EM, GMM_EMtest
from labcode.kmeans import K_Means


def readfile():
    f1 = open('../iofile/irisdata.txt', 'r')
    f2 = open('../iofile/irisdata2.txt', 'w+')
    sourceInLine = f1.readlines()
    dataset = []
    for line in sourceInLine:
        temp1 = line.strip('\n')
        dataset.append(temp1)
        np.random.shuffle(dataset)
    dataset_len = len(dataset)
    for x in range(0, dataset_len):
        f2.write(str(dataset[x]) + '\n')
    f1.close()
    f2.close()
    dataList = []
    labelList = []
    dataTestList = []
    labelTestList = []
    transList = []
    f = open('../iofile/irisdata2.txt', 'r')
    lineInt = 0
    for line in f.readlines():
        lineInt += 1
        lineArr = line.strip().split(",")
        if lineInt <= 60:
            dataList.append([float(lineArr[0]), float(lineArr[1])])
            labelList.append(lineArr[4])
    F = open('../iofile/irisdata.txt', 'r')
    lineInt = 0
    for line in F.readlines():
        lineInt += 1
        lineArr = line.strip().split(",")
        if lineInt >= 101:
            dataTestList.append([float(lineArr[0]), float(lineArr[1])])
            labelTestList.append(lineArr[4])
    return dataList, dataTestList


if __name__ == '__main__':
    a, b = readfile()
    #kmeans_test.selftest()
    #kmeans_test.ucitest(a, b)
    GMM_EMtest.mytest()
    GMM_EMtest.ucitest(a, b)
