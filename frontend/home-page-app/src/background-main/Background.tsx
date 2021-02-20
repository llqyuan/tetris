import React, { SyntheticEvent } from "react";
import MainPanel from "./MainPanel";
import ControlsOverlay from "../overlays/ControlsOverlay";

interface BackgroundProps {}

interface BackgroundState {
    controlsOverlayOn: boolean;
    highscoreOverlayOn: boolean;
}

/** Intended to control which layers of the page are visible.
 * Stores main panel, as well as state for overlays */
class Background 
extends React.Component<BackgroundProps, BackgroundState> {

    msg: string = "tetst";

    constructor(props: any) {
        super(props);
        this.state = {controlsOverlayOn: false, highscoreOverlayOn: false};

        this.openControlsOverlay = this.openControlsOverlay.bind(this);
        this.openHighscoreOverlay = this.openHighscoreOverlay.bind(this);
        this.closeControlsOverlay = this.closeControlsOverlay.bind(this);
        this.closeHighscoreOverlay = this.closeHighscoreOverlay.bind(this);
    }

    render(): React.ReactNode {
        return (
            <div>
                <MainPanel 
                    handleClickControls={this.openControlsOverlay} 
                    handleClickHighScore={this.openHighscoreOverlay}/>
                <ControlsOverlay 
                    toggledOn={this.state.controlsOverlayOn}
                    rerenderParent={this.closeControlsOverlay}/>
            </div>
        );
    }

    openControlsOverlay(e: SyntheticEvent): void {
        e.preventDefault();
        console.log("Clicked controls button, set state to rerender, " + this.msg);
    }

    openHighscoreOverlay(e: SyntheticEvent): void {
        e.preventDefault();
        console.log("Clicked high score button, set state to rerender, " + this.msg);
    }

    closeControlsOverlay(): void {
        console.log("Close controls overlay, set state to rerender, " + this.msg);
    }

    closeHighscoreOverlay(): void {
        console.log("Close high score overlay, set state to rerender, " + this.msg);
    }
}

export default Background;