import React, { SyntheticEvent } from "react";
import Button from "./Button";

interface ControlsButtonProps {
    handleClick: (e: SyntheticEvent) => void;
}

interface ControlsButtonState {}

/** Controls button. Intended to open a 
 * window that shows the game controls. 
 * Props:
 * - handleClick: Callback to use whenver this is clicked
 * */
class ControlsButton 
extends React.Component<ControlsButtonProps, ControlsButtonState> {
    constructor(props: any) {
        super(props);
    }

    render(): React.ReactNode {
        return (
            <Button text="Controls" handleClick={this.props.handleClick}/>
        );
    }
}

export default ControlsButton;