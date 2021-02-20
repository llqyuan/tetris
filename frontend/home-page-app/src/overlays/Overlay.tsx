import React, { SyntheticEvent } from "react";
import CloseOverlayButton from "../buttons/CloseOverlayButton";
import "./Overlay.css"

interface OverlayProps {
    title: string;
    contents: React.ReactNode;
    toggledOn: boolean;
    rerenderParent: () => void;
}

interface OverlayState {}

/** Props:
 * - title: Title of overlay panel
 * - contents: Inner contents of overlay
 * - toggledOn: True if overlay should be visible, 
 * and false if it should be invisible
 * - rerenderParent: Callback to use when clicking the close button
 */
class Overlay 
extends React.Component<OverlayProps, OverlayState> {
    constructor(props: any) {
        super(props);
        this.rerenderParent = this.rerenderParent.bind(this);
    }

    render(): React.ReactNode {
        let displayString: string = "hidden";
        if (this.props.toggledOn) {
            displayString = "block";
        }
        return (
            <div>
                <div style={{display: displayString}} className="opened-panel">
                    <h2> {this.props.title} </h2>
                    this.props.contents;
                    <CloseOverlayButton handleClick={this.rerenderParent}/>
                </div>
            </div>
        );
    }

    rerenderParent(e: SyntheticEvent): void {
        e.preventDefault();
        this.props.rerenderParent();
        console.log("Closing overlay, parent's rerendering should set this invisible");
    }
}

export default Overlay;