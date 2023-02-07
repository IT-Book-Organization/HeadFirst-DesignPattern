package main.java.chapter_12.mvc;

import main.java.chapter_12.mvc.controller.BeatController;
import main.java.chapter_12.mvc.controller.ControllerInterface;
import main.java.chapter_12.mvc.model.BeatModel;
import main.java.chapter_12.mvc.model.BeatModelInterface;

public class DJTestDrive {

    public static void main (String[] args) {
        BeatModelInterface model = new BeatModel();
		ControllerInterface controller = new BeatController(model);
    }
}
