import numpy as np


def pca(dataMat, K):  # dataMat是原始数据，一个矩阵，K是要降到的维数
    meanVals = np.mean(dataMat, axis=0)  # 第一步:求均值
    meanRemoved = dataMat - meanVals  # 减去对应的均值
    covMat = np.cov(meanRemoved, rowvar=0)  # 第二步,求特征协方差矩阵
    eigVals, eigVects = np.linalg.eig(np.mat(covMat))  # 第三步,求特征值和特征向量
    eigValInd = np.argsort(eigVals)  # 第四步,将特征值按照从小到大的顺序排序
    eigValInd = eigValInd[: -(K + 1): -1]  # 选择其中最大的K个
    redEigVects = eigVects[:, eigValInd]  # 然后将其对应的k个特征向量分别作为列向量组成特征向量矩阵.
    lowDDataMat = meanRemoved * redEigVects  # 第五步,将样本点投影到选取的特征向量上,得到降维后的数据
    #print(lowDDataMat)
    print(K)
    reconMat = (lowDDataMat * redEigVects.T) + meanVals  # 还原数据
    return lowDDataMat, reconMat
