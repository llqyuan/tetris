import React, { SyntheticEvent } from "react";
import Button from "./Button";

interface HighScoreButtonProps {}
interface HighScoreButtonState {}

/** High score button. Intended to open a window
 * that displays high scores
 */
class HighScoreButton 
extends React.Component<HighScoreButtonProps, HighScoreButtonState> {
    constructor(props: any){
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    render(): React.ReactNode {
        return <Button text="High scores" handleClick={this.handleClick}/>;
    }

    handleClick(e: SyntheticEvent) {
        e.preventDefault();
        console.log("Clicked on high score button");
    }
}

export default HighScoreButton;