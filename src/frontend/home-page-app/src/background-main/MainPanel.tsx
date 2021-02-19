import React, { SyntheticEvent } from "react";
import PlayButton from "../buttons/PlayButton";
import ControlsButton from "../buttons/ControlsButton";
import HighScoreButton from "../buttons/HighScoreButton";
import "./MainPanel.css"

interface MainPanelProps {
    handleClickControls: (e: SyntheticEvent) => void;
    handleClickHighScore: (e: SyntheticEvent) => void;
}

interface MainPanelState {}

/** Middle section -- title, buttons, etc */
class MainPanel extends React.Component<MainPanelProps, MainPanelState> {
    msg: string = "testet";
    
    constructor(props: any) {
        super(props);
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
                        <ControlsButton handleClick={this.props.handleClickControls}/>
                        <HighScoreButton handleClick={this.props.handleClickHighScore}/>
                        
                    </div>
                </div>
                
                <div id="main-right-panel" className="column">
                </div>
            </div>
        );
    }
}

export default MainPanel;