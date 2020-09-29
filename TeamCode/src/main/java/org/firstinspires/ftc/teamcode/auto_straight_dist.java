package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "auto straight drive")
public class auto_straight_dist extends LinearOpMode {
    DcMotor LeftFront;
    DcMotor LeftRear;
    DcMotor RightFront;
    DcMotor RightRear;

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

        // resetting encoders

        LeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        RightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // running the motors after restart

        LeftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        RightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        int i =0;
        waitForStart();
        while (opModeIsActive() && LeftRear.getCurrentPosition()>123200) {
//            telemetry.addData("Left DRIVING TO: %7d ", LeftRear.getCurrentPosition() * -1);
//            telemetry.addData("Right DRIVING TO: %7d ", RightRear.getCurrentPosition());
//            telemetry.addLine("inloop");
//            telemetry.update();

            // variable is postion of encoders relative to each other
            float x = (LeftRear.getCurrentPosition() * -1) - RightRear.getCurrentPosition();
            double motorspeed = 0.3;
            i=i+1;
            telemetry.addData("i",i);

            if (x == 0) {
                LeftFront.setPower(motorspeed);
                LeftRear.setPower(motorspeed);
                RightFront.setPower(motorspeed);
                RightRear.setPower(motorspeed);
                telemetry.addLine("equal");
                telemetry.addData("right side speed", motorspeed);
                telemetry.addData("X value",x);
                telemetry.addData("Left DRIVING TO: %7d ", LeftRear.getCurrentPosition() * -1);
                telemetry.addData("Right DRIVING TO: %7d ", RightRear.getCurrentPosition());
                telemetry.addLine("inloop");
                telemetry.addData("i",i);
                telemetry.update();
                sleep(250);
            } else if (x < -2000 && x < -100) {
                LeftFront.setPower(motorspeed);
                LeftRear.setPower(motorspeed);
                RightFront.setPower(motorspeed + 0.04);
                RightRear.setPower(motorspeed + 0.04);
                telemetry.addLine("50-200");
                telemetry.addData("right side speed", motorspeed + 0.02);
                telemetry.addData("X value",x);
                //    Log.("FTC ROBOT",str(LeftFront.getPower()));
                sleep(250);
            } else if (x < -6500 && x < -2000) {
                LeftFront.setPower(motorspeed);
                LeftRear.setPower(motorspeed);
                RightFront.setPower(motorspeed + 0.05);
                RightRear.setPower(motorspeed + 0.05);
                telemetry.addData("right side speed", motorspeed + 0.05);
                telemetry.addLine("2000-6500");
                telemetry.update();
                sleep(250);
            } else if (x < -100000000 && x < -6500) {
                LeftFront.setPower(motorspeed);
                LeftRear.setPower(motorspeed);
                RightFront.setPower(motorspeed + 0.1);
                RightRear.setPower(motorspeed + 0.1);
                telemetry.addLine("6500-1000000");
                telemetry.addData("right side speed", motorspeed + 0.15);
                telemetry.update();
                sleep(250);
            } else if (x < 100 && x < 2000) {
                LeftFront.setPower(motorspeed);
                LeftRear.setPower(motorspeed);
                RightFront.setPower(motorspeed - 0.04);
                RightRear.setPower(motorspeed - 0.04);
                telemetry.addLine("-50-2000");
                telemetry.addData("right side speed", motorspeed - 0.02);
                telemetry.update();
                sleep(250);
            } else if (x < 2000 && x < 6000) {
                LeftFront.setPower(motorspeed);
                LeftRear.setPower(motorspeed);
                RightFront.setPower(motorspeed - 0.05);
                RightRear.setPower(motorspeed - 0.05);
                telemetry.addLine("-200--600");
                telemetry.addData("right side speed", motorspeed - 0.05);
                telemetry.update();
                sleep(1250);
            } else if (x < 6000 && x < 10000000) {
                LeftFront.setPower(motorspeed);
                LeftRear.setPower(motorspeed);
                RightFront.setPower(motorspeed - 0.1);
                RightRear.setPower(motorspeed - 0.1);
                telemetry.addLine("-600--1000");
                telemetry.addData("right side speed", motorspeed - 0.1);
                telemetry.update();
                sleep(1250);
            }



            telemetry.addData("Left DRIVING TO: %7d ", LeftRear.getCurrentPosition() * -1);
            telemetry.addData("Right DRIVING TO: %7d ", RightRear.getCurrentPosition());
            telemetry.addLine("inloop");
            telemetry.addData("i",i);
            telemetry.update();

            idle();
        }
        telemetry.addLine("end of program");
        sleep(5000);
    }
}

