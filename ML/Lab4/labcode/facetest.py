import numpy as np
import cv2 as cv
import PIL.Image as Image
import tensorflow as tf

from labcode import PCA


def main2():
    im = Image.open('../picturefile/shandianbian.jpg')
    # 显示图片
    # im.show()
    width, height = im.size
    # 灰度化
    im = im.convert("L")
    im.save('../picturefile/mabaoguo1.jpg')
    data = im.getdata()
    data = np.matrix(data, dtype="float") / 255.0
    new_data = np.reshape(data, (height, width))
    print(width)
    ma, recon = PCA.pca(new_data, 30)
    recon = recon * 255.0
    new_im = Image.fromarray(recon)
    new_im.show()
    if new_im.mode == "F":
        new_im = new_im.convert('L')
    new_im.save('../picturefile/mabaoguo2.jpg')
    ima1 = tf.io.gfile.GFile('../picturefile/mabaoguo1.jpg', 'rb').read()
    ima2 = tf.io.gfile.GFile('../picturefile/mabaoguo2.jpg', 'rb').read()
    im1 = tf.image.decode_jpeg(ima1)
    im2 = tf.image.decode_jpeg(ima2)
    # Compute PSNR over tf.uint8 Tensors.
    psnr1 = tf.image.psnr(im1, im2, max_val=255)
    print(psnr1)
    # print(psnr(new_data,recon))
def main3():
    im = Image.open('../picturefile/trump.jpg')
    # 显示图片
    # im.show()
    width, height = im.size
    # 灰度化
    im = im.convert("L")
    im.save('../picturefile/TRUMP1.jpg')
    data = im.getdata()
    data = np.matrix(data, dtype="float") / 255.0
    new_data = np.reshape(data, (height, width))
    print(width)
    ma, recon = PCA.pca(new_data, 30)
    recon = recon * 255.0
    new_im = Image.fromarray(recon)
    new_im.show()
    if new_im.mode == "F":
        new_im = new_im.convert('L')
    new_im.save('../picturefile/TRUMP2.jpg')
    ima1 = tf.io.gfile.GFile('../picturefile/TRUMP1.jpg', 'rb').read()
    ima2 = tf.io.gfile.GFile('../picturefile/TRUMP2.jpg', 'rb').read()
    im1 = tf.image.decode_jpeg(ima1)
    im2 = tf.image.decode_jpeg(ima2)
    psnr1 = tf.image.psnr(im1, im2, max_val=255)
    print(psnr1)
    # print(psnr(new_data,recon))