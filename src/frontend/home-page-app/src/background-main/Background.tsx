import React, { SyntheticEvent } from "react";
import MainPanel from "./MainPanel";

interface BackgroundProps {}

interface BackgroundState {
    controlsOverlayOn: boolean;
    highscoreOverlayOn: boolean;
}

class Background 
extends React.Component<BackgroundProps, BackgroundState> {

    msg: string = "tetst";

    constructor(props: any) {
        super(props);
        this.state = {controlsOverlayOn: false, highscoreOverlayOn: false};

        this.handleClickControls = this.handleClickControls.bind(this);
        this.handleClickHighScore = this.handleClickHighScore.bind(this);
    }

    render(): React.ReactNode {
        return (
            <MainPanel 
                handleClickControls={this.handleClickControls} 
                handleClickHighScore={this.handleClickHighScore}/>
        );
    }

    handleClickControls(e: SyntheticEvent): void {
        e.preventDefault();
        console.log("Clicked controls button, " + this.msg);
    }

    handleClickHighScore(e: SyntheticEvent): void {
        e.preventDefault();
        console.log("Clicked high score button, " + this.msg);
    }

    /*
    Store the state for overlay toggling here?
    */
}

export default Background;