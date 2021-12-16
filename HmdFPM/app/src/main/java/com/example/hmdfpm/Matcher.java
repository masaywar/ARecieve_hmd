package com.example.hmdfpm;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

class Matcher extends Thread{
    private final static int hessianThreshold = 400;

    private MatOfKeyPoint keypointsObject = null;
    private Mat descriptorsObject = null;
    private Mat imgScene = null;

    private boolean isMatched = false;

    public void run() {
            while(true)
            {
                if(descriptorsObject == null || keypointsObject == null) continue;
                try{
                    if(imgScene == null) continue;

                    Mat img = new Mat();
                    Imgproc.cvtColor(imgScene, img, Imgproc.COLOR_RGBA2GRAY);

                    MatOfKeyPoint  keypointsScene = new MatOfKeyPoint();
                    Mat descriptorsScene = new Mat();

                    ORB.create(hessianThreshold).
                    detectAndCompute(img, new Mat(), keypointsScene, descriptorsScene);

                    DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMINGLUT);
                    List<MatOfDMatch> knnMatches = new ArrayList<>();
                    matcher.knnMatch(descriptorsObject, descriptorsScene, knnMatches, 2);

                    float ratioThresh = 0.75f;
                    List<DMatch> listOfGoodMatches = new ArrayList<>();
                    for (int i = 0; i < knnMatches.size(); i++) {
                        if (knnMatches.get(i).rows() > 1) {
                            DMatch[] matches = knnMatches.get(i).toArray();
                            if (matches[0].distance < ratioThresh * matches[1].distance) {
                                listOfGoodMatches.add(matches[0]);
                            }
                        }
                    }

                    Log.d("cv", Integer.toString(listOfGoodMatches.size()));

                    MatOfDMatch goodMatches = new MatOfDMatch();
                    goodMatches.fromList(listOfGoodMatches);

                    isMatched = listOfGoodMatches.size() > 15;
            }
            catch (CvException e){
            }
        }
    }

    public void setImgScene(Mat img)
    {
        imgScene = img;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setKeyPointsObject(KeyPoint[] keyPoints){
        keypointsObject = new MatOfKeyPoint(keyPoints);
    }

    public void setDescriptorsObject(Mat descriptor){
        descriptorsObject = descriptor;
    }
}
