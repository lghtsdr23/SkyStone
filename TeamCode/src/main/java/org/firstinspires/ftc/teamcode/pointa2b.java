package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "pointa2b")
public class pointa2b extends LinearOpMode {
    DcMotor LeftFront;
    DcMotor LeftRear;
    DcMotor RightFront;
    DcMotor RightRear;
    // Gobilda Motor Specs
    double COUNTS_PER_MOTOR_GOBUILDA = 706;    // gobilda
    double DRIVE_GEAR_REDUCTION = 1;    // 1:1
    double WHEEL_DIAMETER_CM = 10;     // mecanum wheels

    double COUNTS_PER_CM_GOBUILDA = ((COUNTS_PER_MOTOR_GOBUILDA * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_CM * Math.PI)) / 2;

    @Override
    public void runOpMode() {
        //defining motors
        LeftFront = hardwareMap.dcMotor.get("MotorLeftFront");
        LeftRear = hardwareMap.dcMotor.get("MotorLeftRear");
        RightFront = hardwareMap.dcMotor.get("MotorRightFront");
        RightRear = hardwareMap.dcMotor.get("MotorRightRear");
        // reversing motors
        LeftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        //waitForStart();
        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("status", "waiting for start command...");
            telemetry.update();



        }
        while (opModeIsActive()) {
            straightDriveEncoder(0.4, 60, 4);
            sleep(1000);
            strafeDriveEncoder(0.7,20,"RIGHT",2.7);
            break;
        }
    }

    public void straightDriveEncoder(double speed, double distanceCM, double timeCutOff) {
        int LeftFrontTarget;
        int backLeftTarget;
        int RightFrontTarget;
        int backRightTarget;
        double end = 0;
        double t = 0;
        // Setting Zero Behavior for Drive Train Motors
        LeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (opModeIsActive()) {

            LeftFront.setMode(DcMotor.RunMode.RESET_ENCODERS);
            RightFront.setMode(DcMotor.RunMode.RESET_ENCODERS);
            LeftRear.setMode(DcMotor.RunMode.RESET_ENCODERS);
            RightRear.setMode(DcMotor.RunMode.RESET_ENCODERS);

            // Determine new target position, and pass to motor controller
            LeftFrontTarget = LeftFront.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA);
            RightFrontTarget = RightFront.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA);
            backLeftTarget = LeftRear.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA);
            backRightTarget = RightRear.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA);


            // set target position to each motor
            LeftFront.setTargetPosition(LeftFrontTarget);
            RightFront.setTargetPosition(RightFrontTarget);
            LeftRear.setTargetPosition(backLeftTarget);
            RightRear.setTargetPosition(backRightTarget);

            // Turn on run to position
            LeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            LeftFront.setPower(Math.abs(speed));
            RightFront.setPower(Math.abs(speed));
            LeftRear.setPower(Math.abs(speed));
            RightRear.setPower(Math.abs(speed));

            t = getRuntime();
            end = getRuntime() + timeCutOff; //TODO THIS CAN BE AUTOMATED

            while (opModeIsActive() && !isStopRequested() &&
                    (getRuntime() <= end) &&
                    (LeftFront.isBusy() || RightFront.isBusy() || LeftRear.isBusy() || RightRear.isBusy())) {

                // Display it for the driver.
                telemetry.addData("RUN TIME CURRENT: ", "" + getRuntime());
                telemetry.addData("RUN TIME END: ", "" + end);
                telemetry.addData("FRONT LEFT MOTOR", " DRIVING TO: %7d CURRENTLY AT: %7d", LeftFrontTarget, LeftFront.getCurrentPosition());
                telemetry.addData("FRONT RIGHT MOTOR", "DRIVING TO: %7d CURRENTLY AT: %7d", RightFrontTarget, RightFront.getCurrentPosition());
                telemetry.addData("BACK LEFT MOTOR", "DRIVING TO: %7d CURRENTLY AT: %7d", backLeftTarget, LeftRear.getCurrentPosition());
                telemetry.addData("BACK RIGHT MOTOR", "DRIVING TO: %7d CURRENTLY AT: %7d", backRightTarget, RightRear.getCurrentPosition());
                telemetry.update();
            }

            telemetry.clearAll();
            telemetry.addData("FINISHED RUN: ", "" + (end - t));
            telemetry.addData("FRONT LEFT MOTOR", " DRIVING TO: %7d CURRENTLY AT: %7d", LeftFrontTarget, LeftFront.getCurrentPosition());
            telemetry.addData("FRONT RIGHT MOTOR", "DRIVING TO: %7d CURRENTLY AT: %7d", RightFrontTarget, RightFront.getCurrentPosition());
            telemetry.addData("BACK LEFT MOTOR", "DRIVING TO: %7d CURRENTLY AT: %7d", backLeftTarget, LeftRear.getCurrentPosition());
            telemetry.addData("BACK RIGHT MOTOR", "DRIVING TO: %7d CURRENTLY AT: %7d", backRightTarget, RightRear.getCurrentPosition());
            telemetry.update();

            // Stop all motion;
            LeftFront.setPower(0);
            RightFront.setPower(0);
            LeftRear.setPower(0);
            RightRear.setPower(0);

            //Turn off run to position
            LeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }
    public void strafeDriveEncoder(double speed, double distanceCM, String direction, double timeCutOff) {
        int frontLeftTarget = 0;
        int backLeftTarget = 0;
        int frontRightTarget = 0;
        int backRightTarget = 0;
        double end = 0;
        double t = 0;
        // Setting Zero Behavior for Drive Train Motors
        LeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        switch (direction) {
            case "LEFT":
                // Determine new target position, and pass to motor controller
                frontLeftTarget = LeftFront.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA * -1.4);
                frontRightTarget = RightFront.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA * 1.4);
                backLeftTarget = LeftRear.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA * 1.4);
                backRightTarget = (RightRear.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA * -1.4));


                // set target position to each motor
                LeftFront.setTargetPosition(frontLeftTarget);
                RightFront.setTargetPosition(frontRightTarget);
                LeftRear.setTargetPosition(backLeftTarget);
                RightRear.setTargetPosition(backRightTarget);

                // Turn on run to position
                LeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                //Meant to calibrate drift in meccanum drive
                LeftFront.setPower(Math.abs(speed) * 1);// Change this if you want the robot to strafe more backwards
                RightFront.setPower(Math.abs(speed) * .85);// Change this if you want the robot to strafe more forwards
                LeftRear.setPower(Math.abs(speed) * 1);// Change this if you want the robot to strafe more forwards
                RightRear.setPower(Math.abs(speed) * 1);// Change this if you want the robot to strafe more backwards

                break;
            case "RIGHT":
                // Determine new target position, and pass to motor controller
                frontLeftTarget = LeftFront.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA * 1.4);
                frontRightTarget = RightFront.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA * -1.4);
                backLeftTarget = LeftRear.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA * -1.4);
                backRightTarget = RightRear.getCurrentPosition() + (int) (distanceCM * COUNTS_PER_CM_GOBUILDA * 1.4);

                // set target position to each motor
                LeftFront.setTargetPosition(frontLeftTarget);
                RightFront.setTargetPosition(frontRightTarget);
                LeftRear.setTargetPosition(backLeftTarget);
                RightRear.setTargetPosition(backRightTarget);

                // Turn on run to position
                LeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                //Meant to calibrate drift in meccanum drive
                LeftFront.setPower(Math.abs(speed) * 1);// Change this if you want the robot to strafe more backwards
                RightFront.setPower(Math.abs(speed) * .85);// Change this if you want the robot to strafe more forwards
                LeftRear.setPower(Math.abs(speed) * 1);// Change this if you want the robot to strafe more forwards
                RightRear.setPower(Math.abs(speed) * 1);// Change this if you want the robot to strafe more backwards

                break;
        }
        if (opModeIsActive()) {

            t = getRuntime();
            end = timeCutOff + getRuntime();//TODO THIS CAN BE AUTOMATED

            while (opModeIsActive() && !isStopRequested() &&
                    (getRuntime() <= end) &&
                    (LeftFront.isBusy() || RightFront.isBusy() || LeftRear.isBusy() || RightRear.isBusy())) {


                //TODO this is where you can correct the robot while its driving
                /*
                Use parallel encoder to the direction in which your traveling
                if(parallel encoder > 0){
                    // this means that the heading has drifted c
                }else if(parallel encoder < 0){
                // this means that the heading has drifted cc
                }
                 */

                // Display it for the driver.
                telemetry.addData("RUN TIME CURRENT: ", "" + getRuntime());
                telemetry.addData("RUN TIME END: ", "" + end);
                telemetry.addData("FRONT LEFT MOTOR", " DRIVING TO: %7d CURRENTLY AT: %7d", frontLeftTarget, LeftFront.getCurrentPosition());
                telemetry.addData("FRONT RIGHT MOTOR", "DRIVING TO: %7d CURRENTLY AT: %7d", frontRightTarget, RightFront.getCurrentPosition());
                telemetry.addData("BACK LEFT MOTOR", "DRIVING TO: %7d CURRENTLY AT: %7d", backLeftTarget, LeftRear.getCurrentPosition());
                telemetry.addData("BACK RIGHT MOTOR", "DRIVING TO: %7d CURRENTLY AT: %7d", backRightTarget, RightRear.getCurrentPosition());
                telemetry.update();
            }

            telemetry.clearAll();
            telemetry.addData("FINISHED RUN: ", "" + (end - t));
            telemetry.update();

            // Stop all motion;
            LeftFront.setPower(0);
            RightFront.setPower(0);
            LeftRear.setPower(0);
            RightRear.setPower(0);

            //Turn off run to position
            LeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }
}
