import React, { SyntheticEvent } from "react";
import Button from "./Button";
import "./CloseOverlayButton.css";

interface CloseOverlayButtonProps {
    handleClick: (e: SyntheticEvent) => void;
}

interface CloseOverlayButtonState {}

/** Intended to close the overlay.
 * Props:
 * - handleClick: Callback to use when clicking the close button
 */
class CloseOverlayButton 
extends React.Component<CloseOverlayButtonProps, CloseOverlayButtonState> {
    constructor(props: any) {
        super(props);
    }

    render(): React.ReactNode {
        return (<Button text="Close" handleClick={this.props.handleClick}/>);
    }
}

export default CloseOverlayButton;