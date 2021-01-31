import numpy as np
import random
class K_Means(object):
    # k是分组数；tolerance‘中心点误差’；max_iter是迭代次数
    def __init__(self, k=2, tolerance=0.0001, max_iter=300):
        self.k = k
        self.tolerance = tolerance
        self.max_iter = max_iter

    def fit(self, data):
        self.centers = {}
        for i in range(self.k):
            self.centers[i] = data[random.randint(0, len(data)-1)]

        for i in range(self.max_iter):
            self.clf = {}
            for i in range(self.k):
                self.clf[i] = []
            for feature in data:
                distances = []
                for center in self.centers:
                    distances.append(np.linalg.norm(feature - self.centers[center]))
                classification = distances.index(min(distances))
                self.clf[classification].append(feature)
            prev_centers = dict(self.centers)
            for c in self.clf:
                self.centers[c] = np.average(self.clf[c], axis=0)

            # '中心点'是否在误差范围
            optimized = True
            for center in self.centers:
                org_centers = prev_centers[center]
                cur_centers = self.centers[center]
                if np.sum((cur_centers - org_centers) / org_centers * 100.0) > self.tolerance:
                    optimized = False
            if optimized:
                break

    def predict(self, p_data):
        distances = [np.linalg.norm(p_data - self.centers[center]) for center in self.centers]
        index = distances.index(min(distances))
        return index
