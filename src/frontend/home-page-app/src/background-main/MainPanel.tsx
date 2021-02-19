import React, { SyntheticEvent } from "react";
import PlayButton from "../buttons/PlayButton";
import ControlsButton from "../buttons/ControlsButton";
import HighScoreButton from "../buttons/HighScoreButton";
import "./MainPanel.css"

class MainPanel extends React.Component {
    msg: string = "testet";
    
    constructor(props: any) {
        super(props);
        this.handleClickControls = this.handleClickControls.bind(this);
        this.handleClickHighScore = this.handleClickHighScore.bind(this);
    }

    render(): React.ReactNode {
        return (
            <div className="row">
                <div id="main-left-panel" className="column">
                    <p id="panel-title">Tetris</p>
                    <div
                        id="button-container" 
                        style={{width: "70%", margin: "auto", alignItems: "center"}}>

                        <PlayButton/>
                        <ControlsButton handleClick={this.handleClickControls}/>
                        <HighScoreButton handleClick={this.handleClickHighScore}/>
                        
                    </div>
                </div>
                
                <div id="main-right-panel" className="column">
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