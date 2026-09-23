package org.firstinspires.ftc.teamcode.UtilOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.Command;

import static com.pedropathing.api.Paths.*;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.groups.Groups.sequential;


/**
 * A test class to learn PedroPathing V3
 */
@Autonomous
public class PedroPathingTest extends OpMode {
    private  Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();

    //poses
    private final Pose startPos = poseFactory.of(24, 24, 0);
    private final Pose scorePose = poseFactory.of(48,48,90);
    private final Pose parkPose = poseFactory.of(72, 48, 90);

    //poses helper methods???
    private Path startToScore(){
        return line(startPos, scorePose).linear(startPos, scorePose);
    }

    //go to park Spot
    private Path park(){
        return line(scorePose, parkPose).linear(scorePose, parkPose);
    }

    private Command autoRoutime(){
        return sequential(
                follow(follower, startToScore()),
                //add Poses here
                follow(follower, park())
        );
    }

    /**
     * runs at
     */
    @Override
    public void init(){
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPos);
        follower.update();
    }

    @Override
    public void start(){
        schedule(autoRoutime());
    }

    @Override
    public void loop(){
        follower.update();
        Scheduler.execute();
    }


}

