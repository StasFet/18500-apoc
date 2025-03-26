package org.firstinspires.ftc.teamcode.state;

public class SubsystemStates {
    public enum OuttakeState {
        IDLE,
    }

    public enum IntakeStates {
        IDLE,
        ACTIVATED,
        EXTENDING,
        SPINNING,
        SEARCHING_FOR_SAMPLE,
        EJECTING,
        RETRACTING,
        TRANSFER
    }

    public enum HangStates {
        DOWN,
        EXTENDING,
        EXTENDED,
        RETRACTING,
        RETRACTED
    }
}
