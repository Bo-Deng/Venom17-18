package org.firstinspires.ftc.teamcode;

/**
 * Created by Bo on 2/28/2017.
 */

import android.graphics.Bitmap;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.CameraCalibration;
import com.vuforia.Frame;
import com.vuforia.HINT;
import com.vuforia.Image;
import com.vuforia.Matrix34F;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Tool;
import com.vuforia.Vec3F;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Arrays;

@Autonomous(name = "ImageTest", group = "Vuforia")
public class VuforiaTest extends LinearOpMode {


    int BEACON_NOT_VISIBLE = 0;
    int BEACON_RED_BLUE = 1;
    int BEACON_BLUE_RED = 2;
    int BEACON_ALL_BLUE = 3;
    int BEACON_ALL_RED = 4;

    public void runOpMode() throws InterruptedException {

        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AXb/g5n/////AAAAGSUed2rh5Us1jESA1cUn5r5KDUqTfwO2woh7MxjiLKSUyDslqBAgwCi0Qmc6lVczErnF5TIw7vG5R4TJ2igvrDVp+dP+3i2o7UUCRRj/PtyVgb4ZfNrDzHE80/6TUHifpKu4QCM04eRWYZocWNWhuRfytVeWy6NSTWefM9xadqG8FFrFk3XnvqDvk/6ZAgerNBdq5SsJ90eDdoAhgYEee40WxasoUUM9YVMvkWOqZgHSuraV2IyIUjkW/u0O+EkFtTNRUWP+aZwn1qO1H4Lk07AJYe21eqioBLMdzY7A8YqR1TeQ//0WJg8SFdXjuGbF6uHykBe2FF5UeyaehA0iTqfPS+59FLm8y1TuUt57eImq";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer localizer = ClassFactory.createVuforiaLocalizer(params);

        Frame Ftest = new Frame();
        VuforiaLocalizer.CloseableFrame test = new VuforiaLocalizer.CloseableFrame(Ftest);

        waitForStart();

        getImageFromFrame(test, 1);
    }

    public Image getImageFromFrame(VuforiaLocalizer.CloseableFrame frame, int pixelFormat) {
        long numImgs = frame.getNumImages();

        for (int i = 0; i < numImgs; i++) {
            if (frame.getImage(i).getFormat() == pixelFormat)
                return frame.getImage(i);
        }
        return null;
    }


}

