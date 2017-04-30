/**
 * @file objectDetection2.cpp
 * @author A. Huaman ( based in the classic facedetect.cpp in samples/c )
 * @brief A simplified version of facedetect.cpp, show how to load a cascade classifier and how to find objects (Face + eyes) in a video stream - Using LBP here
 */
#include "opencv2/objdetect.hpp"
#include "opencv2/videoio.hpp"
#include "opencv2/highgui.hpp"
#include "opencv2/imgproc.hpp"

#include <iostream>
#include <stdio.h>

using namespace std;
using namespace cv;

/** Function Headers */
void detectAndDisplay( Mat frame );

/** Global variables */
String face_cascade_name = "lbpcascade_frontalface.xml";
String eyes_cascade_name = "haarcascade_eye_tree_eyeglasses.xml";
CascadeClassifier car_cascade;
CascadeClassifier eyes_cascade;
String window_name = "Capture - Face detection";

string cascadeName = "cascade.xml";
string inputName;
bool isInputImage = false;
/**
 * @function main
 */
int main( int argc, char **argv )
{
    //VideoCapture capture;
    VideoCapture capture;/*("nvcamerasrc ! video/x
raw(memory:NVMM), width=(int)1280, height=(int)720,format=(string)I420, framerate=(fraction)24/1 ! nvvidconv flip-method=2 ! video/x-raw, format=(string)BGRx ! videoconvert ! video/x-raw, format=(string)BGR ! appsink"); //open the default camera*/

    Mat image;
    for (int i = 1; i < argc; ++i)
     {
	if (string(argv[i]) == "--cascade")
	{
		cascadeName = argv[++i];
	}
	else if (!isInputImage)
	{
	inputName = argv[i];
	isInputImage = true;
	}
	else
	{
	cout << "Unknown key: " << argv[i] << endl;
	return -1;
	}
    }

    //-- 1. Load the cascade
    if( !car_cascade.load( cascadeName ) ){ printf("--(!)Error loading face cascade\n"); return -1; };
    //if( !eyes_cascade.load( eyes_cascade_name ) ){ printf("--(!)Error loading eyes cascade\n"); return -1; };

    //-- 2. Read the video stream
    //capture.open( -1 );
    //if ( ! capture.isOpened() ) { printf("--(!)Error opening video capture\n"); return -1; }

    if (isInputImage)
    {
        image = imread(inputName);
        CV_Assert(!image.empty());
    }

        //-- 3. Apply the classifier to the frame
        detectAndDisplay( image );

        //-- bail out if escape was pressed
   while(1)
    {
        int c = waitKey(10);
        if( (char)c == 27 ) { break; }
    }
    return 0;
}

/**
 * @function detectAndDisplay
 */
void detectAndDisplay( Mat frame )
{
    std::vector<Rect> cars;
    Mat frame_gray;

    cvtColor( frame, frame_gray, COLOR_BGR2GRAY );
    equalizeHist( frame_gray, frame_gray );

    //-- Detect faces
    car_cascade.detectMultiScale( frame_gray, cars, 1.1, 2, 0, Size(48, 24) );

    for( size_t i = 0; i < cars.size(); i++ )
    {
        Mat carROI = frame_gray( cars[i] );
        std::vector<Rect> car_focussed;

        //-- In each face, detect eyes
        car_cascade.detectMultiScale( carROI, car_focussed, 1.1, 2, 0, Size(48, 24) );
        if(car_focussed.size() >0)
	{
	rectangle(frame_gray, cars[i], Scalar(255));
	}

    }
    cv::cvtColor(frame_gray, frame, COLOR_GRAY2BGR);
    //-- Show what you got
    imshow( window_name, frame );
}
