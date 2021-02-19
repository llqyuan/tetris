import React from "react";
import Button from "./Button";

interface PlayButtonProps {}
interface PlayButtonState {}

/** Specialized play button. Intended to navigate to the game's page */
class PlayButton 
extends React.Component<PlayButtonProps, PlayButtonState> {
    constructor(props: any) {
        super(props);
    }

    render() : React.ReactNode {
        return (
            <Button text="Play" url="./game"/>
        );
    }
}

export default PlayButton;