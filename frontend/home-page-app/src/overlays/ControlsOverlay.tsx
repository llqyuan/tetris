import React, { SyntheticEvent } from "react";
import Overlay from "./Overlay";

interface ControlsOverlayProps {
    toggledOn: boolean;
    rerenderParent: () => void;
}
interface ControlsOverlayState {}

/** Shows controls for game.
 * Props:
 * - toggledOn: True if overlay should be visible and false otherwise
 * - rerenderParent: Callback to pass to Overlay
 */
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
            toggledOn={this.props.toggledOn}
            rerenderParent={this.props.rerenderParent}/>;
    }
}

export default ControlsOverlay;