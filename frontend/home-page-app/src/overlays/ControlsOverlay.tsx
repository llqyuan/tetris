import React from "react";
import Overlay from "./Overlay";

interface ControlsOverlayProps {
    toggledOn: boolean;
}
interface ControlsOverlayState {}

/** Shows controls for game */
class ControlsOverlay 
extends React.Component<ControlsOverlayProps, ControlsOverlayState> {
    constructor(props: any) {
        super(props);
    }

    render(): React.ReactNode {
        let innerText: React.ReactNode = (
            <p> testing ifeiofejiofew</p>
        );
        return <Overlay 
            title="Controls" 
            contents={innerText} 
            toggledOn={this.props.toggledOn}/>;
    }
}

export default ControlsOverlay;