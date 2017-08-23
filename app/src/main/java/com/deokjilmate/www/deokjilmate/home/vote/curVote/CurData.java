package com.deokjilmate.www.deokjilmate.home.vote.curVote;

/**
 * Created by ash on 2017-08-20.
 */

public class CurData {
    String program_name;
    String program_data;

    public CurData(String program_name, String program_data) {
        this.program_name = program_name;
        this.program_data = program_data;
    }

    public String getProgram_name() {
        return program_name;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public String getProgram_data() {
        return program_data;
    }

    public void setProgram_data(String program_data) {
        this.program_data = program_data;
    }
}
