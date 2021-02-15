import cv2
import numpy as np
from random import shuffle
img = cv2.imread("/Users/sam.corzine/Downloads/swordfishtrombones.jpg")
# img = img[0:1280, 0:960]
outimage = np.zeros((1280, 960, 3), dtype="uint8")
xcount = 16
ycount = 12
stridex = 1280//xcount
stridey = 960//ycount
sequence = list(range(xcount))
shuffle(sequence)
for x in range(xcount):
    i = x+1
    startx = x * stridex
    endx = (x+1) * stridex
    fromstartx = sequence[x] * stridex
    fromendx = (sequence[x] + 1) * stridex
    sequencey = list(range(ycount))
    shuffle(sequencey)
    for y in range(ycount):
    #     sequencey = list(range(ycount))
    #     shuffle(sequencey)
        starty = y * stridey
        endy = (y+1) * stridey
        fromstarty = sequencey[y] * stridey
        fromendy = (sequencey[y] + 1) * stridey
        outimage[startx:endx, starty:endy] = img[fromstartx:fromendx, fromstarty:fromendy]
cv2.namedWindow("image", cv2.WINDOW_NORMAL)
cv2.imshow('image', outimage)
cv2.waitKey(0)
cv2.destroyAllWindows()