from labcode import kmeans
from matplotlib import pyplot
import numpy as np
from labcode.kmeans import K_Means


def selftest():
    pyplot.subplot(1, 1, 1)
    train_x = np.zeros((60, 2))
    test_x = np.zeros((100, 2))
    train_x[:20, :] = np.random.multivariate_normal([0.6, 0.4], [[0.1, 0], [0, 0.1]], size=20)
    train_x[20:, :] = np.random.multivariate_normal([-0.6, -0.4], [[0.1, 0], [0, 0.1]], size=40)
    train_x[40:, :] = np.random.multivariate_normal([0.0, 0.0], [[0.1, 0], [0, 0.1]], size=20)
    test_x[:100, :] = np.random.multivariate_normal([0.6, 0.4], [[0.1, 0], [0, 0.1]], size=100)

    k_means = K_Means(k=3)
    k_means.fit(train_x)
    for center in k_means.centers:
        pyplot.scatter(k_means.centers[center][0], k_means.centers[center][1], marker='*', s=150)

    print(k_means.centers)
    for cat in k_means.clf:
        for point in k_means.clf[cat]:
            if cat == 0:
                pyplot.scatter(point[0], point[1], c='r')
            if cat == 1:
                pyplot.scatter(point[0], point[1], c='b')
            if cat == 2:
                pyplot.scatter(point[0], point[1], c='y')
    pyplot.show()

    count1 = 0
    count2 = 0
    count3 = 0
    for point in test_x:
        cat = k_means.predict(point)
        if cat == 0:
            count1 += 1
            pyplot.scatter(point[0], point[1], c='r')
        if cat == 1:
            count2 += 1
            pyplot.scatter(point[0], point[1], c='b')
        if cat == 2:
            count3 += 1
            pyplot.scatter(point[0], point[1], c='y')
    print('red:', count1, 'blue:', count2, "yellow:", count3)
    pyplot.show()


def ucitest(dataList,dataTestList):
    pyplot.subplot(1,1,1)
    k_means = K_Means(k=3)
    k_means.fit(np.array(dataList))
    for center in k_means.centers:
        pyplot.scatter(k_means.centers[center][0], k_means.centers[center][1], marker='*', s=150)
    print(k_means.centers)
    for cat in k_means.clf:
        for point in k_means.clf[cat]:
            if cat == 0:
                pyplot.scatter(point[0], point[1], c='r')
            if cat == 1:
                pyplot.scatter(point[0], point[1], c='b')
            if cat == 2:
                pyplot.scatter(point[0], point[1], c='y')
    pyplot.show()

    count1 = 0
    count2 = 0
    count3 = 0
    for point in np.array(dataTestList):
        cat = k_means.predict(point)
        if cat == 0:
            count1 += 1
            pyplot.scatter(point[0], point[1], c='r')
        if cat == 1:
            count2 += 1
            pyplot.scatter(point[0], point[1], c='b')
        if cat == 2:
            count3 += 1
            pyplot.scatter(point[0], point[1], c='y')
    print('red:', count1, 'blue:', count2, "yellow:", count3)
    pyplot.show()
    return 0
