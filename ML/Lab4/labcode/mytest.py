import numpy as np
from matplotlib import pyplot as plt
from labcode import PCA
from mpl_toolkits.mplot3d import Axes3D


def generate_data(number):
    mean = [1, 2, 3]
    cov = [[0.01, 0, 0], [0, 1, 0], [0, 0, 1]]
    sample_data = []
    for index in range(number):
        sample_data.append(np.random.multivariate_normal(mean, cov).tolist())
    return np.array(sample_data)


def draw_data(origin_data, pca_data):
    x_list = list(origin_data)
    y_list = list(np.array(pca_data))
    fig = plt.figure()  # 得到画面
    ax = fig.gca(projection='3d')  # 得到3d坐标的图
    # 画点
    for x in x_list:
        ax.scatter(x[0], x[1], x[2], c='r')
    for y in y_list:
        ax.scatter(y[0], y[1], y[2], c='b')

    plt.show()


def main():
    origin_data = generate_data(100)
    lowData, Recondata = PCA.pca(origin_data, 2)
    #print(Recondata)
    draw_data(origin_data, Recondata)
    # print(origin_data)
