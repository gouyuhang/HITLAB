import numpy as np
import matplotlib.pyplot as plt
from matplotlib.patches import Ellipse
from scipy.stats import multivariate_normal
from labcode import kmeans

# 更新W
from labcode.kmeans import K_Means


def update_W(X, Mu, Var, Pi):
    n_points, n_clusters = len(X), len(Pi)
    pdfs = np.zeros(((n_points, n_clusters)))
    for i in range(n_clusters):
        pdfs[:, i] = Pi[i] * multivariate_normal.pdf(X, Mu[i], np.diag(Var[i]))
    W = pdfs / pdfs.sum(axis=1).reshape(-1, 1)
    return W


# 更新pi
def update_Pi(W):
    Pi = W.sum(axis=0) / W.sum()
    return Pi


# 计算log似然函数
def logLH(X, Pi, Mu, Var):
    n_points, n_clusters = len(X), len(Pi)
    pdfs = np.zeros(((n_points, n_clusters)))
    for i in range(n_clusters):
        pdfs[:, i] = Pi[i] * multivariate_normal.pdf(X, Mu[i], np.diag(Var[i]))
    return np.mean(np.log(pdfs.sum(axis=1)))


# 更新Mu
def update_Mu(X, W):
    n_clusters = W.shape[1]
    Mu = np.zeros((n_clusters, 2))
    for i in range(n_clusters):
        Mu[i] = np.average(X, axis=0, weights=W[:, i])
    return Mu


# 更新Var
def update_Var(X, Mu, W):
    n_clusters = W.shape[1]
    Var = np.zeros((n_clusters, 2))
    for i in range(n_clusters):
        Var[i] = np.average((X - Mu[i]) ** 2, axis=0, weights=W[:, i])
    return Var


def train(X):
    # 初始化
    n_clusters = 3
    n_points = len(X)
    k_means = K_Means(k=3)
    k_means.fit(X)
    Mu = [[k_means.centers[0][0], k_means.centers[0][1]],
          [k_means.centers[1][0], k_means.centers[1][1]],
          [k_means.centers[2][0], k_means.centers[2][1]],
          ]
    Var = [[0.1, 0.1], [0.1, 0.1], [0.1, 0.1]]
    Pi = [1 / n_clusters] * 3
    W = np.ones((n_points, n_clusters)) / n_clusters
    Pi = W.sum(axis=0) / W.sum()
    # 迭代
    loglh = []
    ClassList = []
    for i in range(10):
        # plot_clusters(X, Mu, Var, true_Mu, true_Var)
        loglh.append(logLH(X, Pi, Mu, Var))
        W = update_W(X, Mu, Var, Pi)
        Pi = update_Pi(W)
        Mu = update_Mu(X, W)
        print('log-likehood:%.3f'%loglh[-1])
        Var = update_Var(X, Mu, W)
    for i in range(W.shape[0]):
        b = np.argmax(W[i, :])
        ClassList.append(b)
    return Mu, Var, Pi, ClassList


def predict(X, Mu, Var, Pi):
    n_points, n_clusters = len(X), len(Pi)
    pdfs = np.zeros(((n_points, n_clusters)))
    for i in range(n_clusters):
        pdfs[:, i] = Pi[i] * multivariate_normal.pdf(X, Mu[i], np.diag(Var[i]))
    W = pdfs / pdfs.sum(axis=1).reshape(-1, 1)
    ClassList = []
    for i in range(W.shape[0]):
        b = np.argmax(W[i, :])
        ClassList.append(b)
    return ClassList
