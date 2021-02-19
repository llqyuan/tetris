import React, { SyntheticEvent } from "react";
import PlayButton from "../buttons/PlayButton";
import ControlsButton from "../buttons/ControlsButton";
import HighScoreButton from "../buttons/HighScoreButton";

class MainPanel extends React.Component {
    msg: string = "testet";
    
    constructor(props: any) {
        super(props);
        this.handleClickControls = this.handleClickControls.bind(this);
        this.handleClickHighScore = this.handleClickHighScore.bind(this);
    }

    render(): React.ReactNode {
        return (
            <div style={{width: "100%", alignItems: "center"}}>
                <div id="main-panel-title">Tetris</div>
                <div 
                    id="button-container" 
                    style={{width: "40%", margin: "auto", alignItems: "center"}}>

                    <PlayButton/>
                    <ControlsButton handleClick={this.handleClickControls}/>
                    <HighScoreButton handleClick={this.handleClickHighScore}/>
                    
                </div>
            </div>
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
}

export default MainPanel;