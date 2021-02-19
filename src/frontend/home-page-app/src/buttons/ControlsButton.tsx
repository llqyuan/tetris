import React, { SyntheticEvent } from "react";
import Button from "./Button";

interface ControlsButtonProps {}
interface ControlsButtonState {}

/** Controls button. Intended to open a 
 * window that shows the game controls. */
class ControlsButton 
extends React.Component<ControlsButtonProps, ControlsButtonState> {
    constructor(props: any) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    render(): React.ReactNode {
        return (
            <Button text="Controls" handleClick={this.handleClick}/>
        );
    }

    handleClick(e: SyntheticEvent): void {
        e.preventDefault();
        console.log("Clicked on controls button");
    }
}

export default ControlsButton;