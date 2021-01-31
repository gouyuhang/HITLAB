import numpy as np
from matplotlib import pyplot as plt


def generateData(number, scale=0.3):
    X = np.linspace(0, 1, num=number)
    T = np.sin(2 * np.pi * X) + np.random.normal(scale=scale, size=X.shape)
    return X, T


def transform(X, degree=2):
    X_T = X.transpose()
    X = np.transpose([X])
    features = [np.ones(len(X))]
    for i in range(0, degree):
        features.append(np.multiply(X_T, features[i]))

    return np.asarray(features).transpose()


def conjugategradient(A, b, degree):
    w = np.zeros(degree + 1)
    r_0 = b - A @ w
    p = r_0
    k = 0
    while True:
        k = k + 1
        alpha = (r_0.T @ r_0) / (p.T @ A @ p)
        w = w + alpha * p
        r = r_0 - alpha * A @ p
        if (r.T @ r) < 1e-10:
            print(k)
            break
        beta = (r.T @ r) / (r_0.T @ r_0)
        p = r + beta * p
        r_0 = r
    return w


def gradientdescent(X, Y, degree, rate, lam):
    k = 0
    w = np.zeros(degree + 1)
    temp = X @ w - Y
    loss_0 = 0.5 * (temp.T @ temp + lam * w @ w.T)
    while True:
        k = k + 1
        w = w - rate * (X.T @ X @ w + lam * w - X.T @ Y)
        temp = X @ w - Y
        loss = 0.5 * (temp.T @ temp + lam * w @ w.T)
        if np.abs(loss - loss_0) < 1e-7:
            print(k)
            return w
        else:
            loss_0 = loss


number_train = 10  # 训练样本的数量
number_test = 100  # 测试样本的数量
degree = 12  # 多项式的阶数
X_training, Y_training = generateData(number_train)
X_test = np.linspace(0, 1, number_test)
X_Train = transform(X_training, degree=degree)
X_Test = transform(X_test, degree=degree)
Y = np.sin(2 * np.pi * X_test)
plt.figure(figsize=(20, 10))
title = "degree = " + str(degree) + ", number_train = " + \
        str(number_train) + ", number_test = " + str(number_test)
# 解析解(不带正则项)
plt.subplot(2, 2, 1)
plt.ylim(-1.5, 1.5)
plt.scatter(X_training, Y_training, facecolor="none",
            edgecolor="b", label="training data")
plt.plot(X_test, Y, "g", label="$\sin(2\pi x)$")

# 求解w 最基本的方法拟合
w_reg = np.linalg.solve(X_Train.T @ X_Train, X_Train.T @ Y_training)
plt.plot(X_test, np.dot(X_Test, w_reg),
         "r", label="analytical solution")
plt.title(title)
plt.legend()

# 从-50到1次方测试lamba解析解带正则项
lamTestList = []
lamTrainList = []
lamList = range(-50, 1)
for lam in lamList:
    w_lam = np.linalg.solve(np.identity(len(X_Train.T)) * np.exp(lam) + X_Train.T @ X_Train, X_Train.T @ Y_training)
    Y_test = np.dot(X_Test, w_lam)
    Y_train = np.dot(X_Train, w_lam)
    lamTestList.append(np.sqrt(np.mean(np.square(Y_test - Y))))
    lamTrainList.append(np.sqrt(np.mean(np.square(Y_train - Y_training))))
bestLam = lamList[np.where(lamTestList == np.min(lamTestList))[0][0]]
print("bestLam:", bestLam, np.min(lamTestList))
plt.subplot(2, 2, 2)
plt.ylabel("$E_{RMS}$")
plt.ylim(0, 1)
plt.xlabel("$ln \lambda$")
plt.plot(lamList, lamTestList, 'o-', mfc="none", mec="b", ms=5,
         label="Test")
plt.plot(lamList, lamTrainList, 'o-', mfc="none", mec="r", ms=5,
         label="Train")
plt.legend()

# 最佳lamba带惩罚项的解析解图像
title = "degree = " + str(degree) + ", number_train = " + \
        str(number_train) + ", number_test = " + str(number_test)
plt.subplot(2, 2, 3)
plt.ylim(-1.5, 1.5)
plt.scatter(X_training, Y_training, facecolor="none",
            edgecolor="b", label="training data")
plt.plot(X_test, Y, "g", label="$\sin(2\pi x)$")
w_lamBest = np.linalg.solve(np.identity(len(X_Train.T)) * np.exp(bestLam) + X_Train.T @ X_Train, X_Train.T @ Y_training)
plt.plot(X_test, np.dot(X_Test, w_lamBest),
         "r", label="analytical solution")
plt.title(title)
plt.legend()
# 共轭梯度法优化（是否正则化）
title = "degree = " + str(degree) + ", number_train = " + \
        str(number_train) + ", number_test = " + str(number_test)
plt.subplot(2, 2, 4)
plt.ylim(-1.5, 1.5)
plt.scatter(X_training, Y_training, facecolor="none",
            edgecolor="b", label="training data")
plt.plot(X_test, Y, "g", label="$\sin(2\pi x)$")
w_cj = conjugategradient(X_Train.T @ X_Train, X_Train.T @ Y_training, degree)
w_cj_reg = conjugategradient(np.identity(len(X_Train.T)) * np.exp(bestLam) + X_Train.T @ X_Train,
                             X_Train.T @ Y_training,
                             degree)
plt.plot(X_test, np.dot(X_Test, w_cj),
         "r", label="cj solution")
plt.plot(X_test, np.dot(X_Test, w_cj_reg),
         "b", label="cj regulation solution")
plt.title(title)
plt.legend()
plt.show()
# 最速下降法
title = "degree = " + str(degree) + ", number_train = " + \
        str(number_train) + ", number_test = " + str(number_test)
plt.subplot(2, 2, 1)
plt.ylim(-1.5, 1.5)
plt.scatter(X_training, Y_training, facecolor="none",
            edgecolor="b", label="training data")
plt.plot(X_test, Y, "g", label="$\sin(2\pi x)$")
w_gd = gradientdescent(X_Train, Y_training, degree, 0.02, 0)
w_gd_reg = gradientdescent(X_Train, Y_training, degree, 0.01, np.exp(bestLam))
plt.plot(X_test, np.dot(X_Test, w_gd),
         "r", label="gd solution")
plt.plot(X_test, np.dot(X_Test, w_gd_reg),
         "b", label="gd regulation solution")
plt.title(title)
plt.legend()
plt.show()
