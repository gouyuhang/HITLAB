import numpy as np
from matplotlib import pyplot
from labcode import GMM_EM


def mytest():
    pyplot.subplot(1, 1, 1)
    train_x = np.zeros((60, 2))
    test_x = np.zeros((100, 2))
    train_x[:20, :] = np.random.multivariate_normal([0.6, 0.4], [[0.1, 0], [0, 0.1]], size=20)
    train_x[20:, :] = np.random.multivariate_normal([-0.6, -0.4], [[0.1, 0], [0, 0.1]], size=40)
    train_x[40:, :] = np.random.multivariate_normal([0.0, 0.0], [[0.1, 0], [0, 0.1]], size=20)
    test_x[:100, :] = np.random.multivariate_normal([0.6, 0.4], [[0.1, 0], [0, 0.1]], size=100)
    Mu, Var, Pi, ClassList = GMM_EM.train(train_x)
    Clf = GMM_EM.predict(test_x, Mu, Var, Pi)
    print(Mu)
    print(Var)
    for i in range(train_x.shape[0]):
        if ClassList[i] == 0:
            pyplot.scatter(train_x[i][0], train_x[i][1], c='r')
        if ClassList[i] == 1:
            pyplot.scatter(train_x[i][0], train_x[i][1], c='b')
        if ClassList[i] == 2:
            pyplot.scatter(train_x[i][0], train_x[i][1], c='y')
    pyplot.show()
    count1 = 0
    count2 = 0
    count3 = 0
    for i in range(test_x.shape[0]):
        if Clf[i] == 0:
            pyplot.scatter(test_x[i][0], test_x[i][1], c='r')
            count1 += 1
        if Clf[i] == 1:
            pyplot.scatter(test_x[i][0], test_x[i][1], c='b')
            count2 += 1
        if Clf[i] == 2:
            pyplot.scatter(test_x[i][0], test_x[i][1], c='y')
            count3 += 1
    print("red:", count1, "blue:", count2, "yellow:", count3)
    pyplot.show()


def ucitest(dataList,dataTestList):
    pyplot.subplot(1, 1, 1)
    train_x = np.array(dataList)
    test_x = np.array(dataTestList)
    Mu, Var, Pi, ClassList = GMM_EM.train(train_x)
    Clf = GMM_EM.predict(test_x, Mu, Var, Pi)
    print(Mu)
    print(Var)
    for i in range(train_x.shape[0]):
        if ClassList[i] == 0:
            pyplot.scatter(train_x[i][0], train_x[i][1], c='r')
        if ClassList[i] == 1:
            pyplot.scatter(train_x[i][0], train_x[i][1], c='b')
        if ClassList[i] == 2:
            pyplot.scatter(train_x[i][0], train_x[i][1], c='y')
    pyplot.show()
    count1 = 0
    count2 = 0
    count3 = 0
    for i in range(test_x.shape[0]):
        if Clf[i] == 0:
            pyplot.scatter(train_x[i][0], train_x[i][1], c='r')
            count1 += 1
        if Clf[i] == 1:
            pyplot.scatter(train_x[i][0], train_x[i][1], c='b')
            count2 += 1
        if Clf[i] == 2:
            pyplot.scatter(train_x[i][0], train_x[i][1], c='y')
            count3 += 1
    print("red:", count1, "blue:", count2, "yellow:", count3)
    pyplot.show()
