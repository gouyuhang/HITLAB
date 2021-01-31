import matplotlib.pyplot as plt
import numpy as np


# sigmoid函数和初始化数据
def sigmoid(z):
    return 1.0 / (1 + np.exp(-z))


def accuracy(X, Y, beta):
    """ 计算在给定测试集上的分类准确度 """
    m, n = np.shape(X)
    count = 0
    predict = sigmoid(X * beta)
    for index in range(m):
        if predict[index] < 0.5 and Y[index] == 0:
            count = count + 1
        elif predict[index] > 0.5 and Y[index] == 1:
            count = count + 1
    return (1.0 * count) / m


def loadHeartDataSet(filePath):
    dataTrainList = []
    labelTrainList = []
    dataTestList = []
    labelTestList = []
    f = open(filePath)
    lineInt = 0
    for line in f.readlines():
        lineInt += 1
        lineArr = line.strip().split(",")
        if lineInt <= 100:
            dataTrainList.append([1.0, float(lineArr[0]) - 50.0, float(lineArr[3]) - 130.0, float(lineArr[7]) - 150.0])
            if int(lineArr[13]) > 0:
                labelTrainList.append(1)
            else:
                labelTrainList.append(0)
        else:
            dataTestList.append([1.0, float(lineArr[0]) - 50.0, float(lineArr[3]) - 130.0, float(lineArr[7]) - 150.0])
            if int(lineArr[13]) > 0:
                labelTestList.append(1)
            else:
                labelTestList.append(0)
    return dataTrainList, labelTrainList, np.mat(dataTestList), np.mat(labelTestList).T


def loadDataSet(filePath):
    dataList = []
    labelList = []
    dataTestList = []
    labelTestList = []
    f = open(filePath)
    lineInt = 0
    for line in f.readlines():
        lineInt += 1
        lineArr = line.strip().split(",")
        if lineInt <= 100:
            dataList.append([1.0, float(lineArr[0]), float(lineArr[1]), float(lineArr[2]), float(lineArr[3])])
            labelList.append(int(lineArr[4]))
        else:
            if lineInt >= 1001:
                break;
            dataTestList.append([1.0, float(lineArr[0]), float(lineArr[1]), float(lineArr[2]), float(lineArr[3])])
            labelTestList.append(int(lineArr[4]))

    return dataList, labelList, np.asarray(dataTestList), np.mat(labelTestList).T


def loss(X, Y, weight):
    m, n = np.shape(X)
    predict = np.zeros((m, 1))
    for i in range(m):
        predict[i] = X[i] * weight
    t = 0
    for i in range(m):
        t += np.log(1 + np.exp(predict[i]))
    return np.dot(Y.T, predict) - t


def grad_descent(dataMatIn, classLabels, lam):
    dataMat = np.mat(dataMatIn)  # x数据转化为矩阵
    labelMat = np.mat(classLabels).T  # y数据转换为矩阵并转置为列向量
    m, n = np.shape(dataMat)  # 返回矩阵的大小
    alpha = 0.001  # 步长
    maxCycles = 1000  # 迭代次数
    weights = np.ones((n, 1))  # 权重列向量
    # 梯度下降算法
    for k in range(maxCycles):
        h = sigmoid(dataMat * weights)
        error = h - labelMat
        weights = weights - lam * alpha * weights - alpha * dataMat.T * error
        # print(loss(dataMat, labelMat, weights))
    return weights


def plotBestFIt(weights, dataMatIn, classLabels):
    n = np.shape(dataMatIn)[0]
    xcord1 = []
    ycord1 = []
    xcord2 = []
    ycord2 = []
    for i in range(n):
        if classLabels[i] == 1:
            xcord1.append(dataMatIn[i][1])
            ycord1.append(dataMatIn[i][2])
        else:
            xcord2.append(dataMatIn[i][1])
            ycord2.append(dataMatIn[i][2])
    fig = plt.figure()
    ax = fig.add_subplot(111)
    ax.scatter(xcord1, ycord1, s=30, c='red', marker='s')
    ax.scatter(xcord2, ycord2, s=30, c='green')
    x = np.arange(-1, 1, 0.1)
    y = (-weights[0, 0] - weights[1, 0] * x) / weights[2, 0]  # matix
    ax.plot(x, y)
    plt.xlabel("X1")
    plt.ylabel("X2")
    plt.show()


def selfGenerateData(naive, trainNumber, testNumber):
    n_0 = np.ceil(trainNumber / 2).astype(np.int32)
    n_1 = np.ceil(testNumber / 2).astype(np.int32)
    lam = 0.2  # 随机变量方差
    cov_xy = 0.1  # 两个维度的协方差
    x_mean1 = [-0.6, -0.4]  # 类别1的均值
    x_mean2 = [0.6, 0.4]  # 类别2的均值
    train_x = np.zeros((trainNumber, 2))
    train_y = np.zeros(trainNumber)
    test_x = np.zeros((testNumber, 2))
    test_y = np.zeros(testNumber)
    if naive:  # 满足朴素贝叶斯假设
        train_x[:n_0, :] = np.random.multivariate_normal(x_mean1, [[lam, 0], [0, lam]], size=n_0)
        train_x[n_0:, :] = np.random.multivariate_normal(x_mean2, [[lam, 0], [0, lam]], size=trainNumber - n_0)
        train_y[:n_0] = 0
        train_y[n_0:] = 1
        test_x[:n_1, :] = np.random.multivariate_normal(x_mean1, [[lam, 0], [0, lam]], size=n_1)
        test_x[n_1:, :] = np.random.multivariate_normal(x_mean2, [[lam, 0], [0, lam]], size=testNumber - n_1)
        test_y[:n_1] = 0
        test_y[n_1:] = 1
    else:  # 不满足朴素贝叶斯假设
        train_x[:n_0, :] = np.random.multivariate_normal(
            x_mean1, [[lam, cov_xy], [cov_xy, lam]], size=n_0)
        train_x[n_0:, :] = np.random.multivariate_normal(
            x_mean2, [[lam, cov_xy], [cov_xy, lam]], size=trainNumber - n_0)
        train_y[:n_0] = 0
        train_y[n_0:] = 1
        test_x[:n_1, :] = np.random.multivariate_normal(
            x_mean1, [[lam, cov_xy], [cov_xy, lam]], size=n_1)
        test_x[n_1:, :] = np.random.multivariate_normal(
            x_mean2, [[lam, cov_xy], [cov_xy, lam]], size=testNumber - n_1)
        test_y[:n_1] = 0
        test_y[n_1:] = 1
    train_X = np.ones((trainNumber, 3))
    train_X[:, 1] = train_x[:, 0]
    train_X[:, 2] = train_x[:, 1]
    test_X = np.ones((testNumber, 3))
    test_X[:, 1] = test_x[:, 0]
    test_X[:, 2] = test_x[:, 1]
    return train_X, train_y, test_X, test_y


def selfNaiveTest():
    dataTrain, labelTrain, dataTest, labelTest = selfGenerateData(1, 300, 700)
    r_0 = grad_descent(dataTrain, labelTrain, 0)
    r_1 = grad_descent(dataTrain, labelTrain, 0.1)
    plotBestFIt(r_0, dataTest, labelTest)
    #plotBestFIt(r_1, dataTest, labelTest)
    print("accruacy without regulation(naive):", accuracy(dataTest, labelTest, r_0))
    print("accruacy with regulation(naive):", accuracy(dataTest, labelTest, r_1))
    return 0


def selfNonNaiveTest():
    dataTrain, labelTrain, dataTest, labelTest = selfGenerateData(0, 300, 700)
    r_0 = grad_descent(dataTrain, labelTrain, 0)
    r_1 = grad_descent(dataTrain, labelTrain, 0.1)
    plotBestFIt(r_0, dataTest, labelTest)
    #plotBestFIt(r_1, dataTest, labelTest)
    print("accruacy without regulation(nonnaive):", accuracy(dataTest, labelTest, r_0))
    print("accruacy with regulation(nonnaive):", accuracy(dataTest, labelTest, r_1))
    return 0


def heartdisease():
    f1 = open('C:/Users/18245/Desktop/processedclevelanddata.txt', 'r')
    f2 = open('C:/Users/18245/Desktop/a.txt', 'w+')
    sourceInLine = f1.readlines()
    dataset = []
    for line in sourceInLine:
        temp1 = line.strip('\n')
        dataset.append(temp1)
        np.random.shuffle(dataset)
    dataset_len = len(dataset)
    for x in range(0, dataset_len):
        f2.write(str(dataset[x]) + '\n')
    dataMatIn_2, classLabels_2, dataTest_2, labelTest_2 = loadHeartDataSet("C:/Users/18245/Desktop/a.txt")
    r = grad_descent(dataMatIn_2, classLabels_2, 0)
    r_1 = grad_descent(dataMatIn_2, classLabels_2, 0.1)
    # print(r)
    print("heart accuracy without regulation:", accuracy(dataTest_2, labelTest_2, r))
    print("heart accuracy with regulation:", accuracy(dataTest_2, labelTest_2, r_1))


def banknote():
    f1 = open('C:/Users/18245/Desktop/data_banknote_authentication.txt', 'r')
    f2 = open('C:/Users/18245/Desktop/b.txt', 'w+')
    sourceInLine = f1.readlines()
    dataset = []
    for line in sourceInLine:
        temp1 = line.strip('\n')
        dataset.append(temp1)
        np.random.shuffle(dataset)
    dataset_len = len(dataset)
    for x in range(0, dataset_len):
        f2.write(str(dataset[x]) + '\n')
    dataMatIn_2, classLabels_2, dataTest_2, labelTest_2 = loadDataSet("C:/Users/18245/Desktop/b.txt")
    r = grad_descent(dataMatIn_2, classLabels_2, 0)
    r_1 = grad_descent(dataMatIn_2, classLabels_2, 0.1)
    # print("w without regulation\n", r)
    print("banknote accuracy without regulation:", accuracy(dataTest_2, labelTest_2, r))
    print("banknote accuracy with regulation:", accuracy(dataTest_2, labelTest_2, r_1))


if __name__ == '__main__':
    #selfNaiveTest()
    #selfNonNaiveTest()
    #banknote()
    heartdisease()
