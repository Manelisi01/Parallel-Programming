from PIL import Image
import numpy as np
import argparse

def images_are_identical(image1_path, image2_path):
    img1 = Image.open(image1_path)
    img2 = Image.open(image2_path)

    if img1.mode != img2.mode or img1.size != img2.size:
        return False

    img1_array = np.array(img1)
    img2_array = np.array(img2)

    return np.array_equal(img1_array, img2_array)

def main():
    parser = argparse.ArgumentParser(description='Compare two images bit by bit.')
    parser.add_argument('image1', type=str, help='Path to the first image')
    parser.add_argument('image2', type=str, help='Path to the second image')

    args = parser.parse_args()

    if images_are_identical(args.image1, args.image2):
        print("The images are identical.")
    else:
        print("The images are different.")

if __name__ == "__main__":
    main()
