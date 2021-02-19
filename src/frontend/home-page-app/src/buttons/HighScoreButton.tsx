import React, { SyntheticEvent } from "react";
import Button from "./Button";

interface HighScoreButtonProps {
    handleClick: (e: SyntheticEvent) => void;
}

interface HighScoreButtonState {}

/** High score button. Intended to open a window
 * that displays high scores.
 * Props:
 * - handleClick: Callback to use whenver this is clicked
 */
class HighScoreButton 
extends React.Component<HighScoreButtonProps, HighScoreButtonState> {

    constructor(props: any){
        super(props);
    }

    render(): React.ReactNode {
        return <Button text="High scores" handleClick={this.props.handleClick}/>;
    }
}

export default HighScoreButton;