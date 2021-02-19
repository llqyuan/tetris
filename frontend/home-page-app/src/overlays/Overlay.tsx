import React, { SyntheticEvent } from "react";
import CloseOverlayButton from "../buttons/CloseOverlayButton";
import "./Overlay.css"

interface OverlayProps {
    title: string;
    contents: React.ReactNode;
    toggledOn: boolean;
}

interface OverlayState {
    toggledOn: boolean;
}


class Overlay 
extends React.Component<OverlayProps, OverlayState> {
    constructor(props: any) {
        super(props);
        this.state = {toggledOn: this.props.toggledOn};
        this.closeOverlay = this.closeOverlay.bind(this);
    }

    render(): React.ReactNode {
        let displayString: string = "hidden";
        if (this.state.toggledOn) {
            displayString = "block";
        }
        return (
            <div>
                <div style={{display: displayString}} className="opened-panel">
                    <h2> title </h2>
                    this.props.contents;
                    <CloseOverlayButton handleClick={this.closeOverlay}/>
                </div>
            </div>
        );
    }

    closeOverlay(e: SyntheticEvent): void {
        e.preventDefault();
        this.setState({toggledOn: false});
        console.log("Closing overlay");
    }
}

export default Overlay;