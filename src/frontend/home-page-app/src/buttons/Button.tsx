import React, { SyntheticEvent } from "react";
import "./Button.css";

interface ButtonProps {
    text: string;
    handleClick?: ( e: SyntheticEvent ) => void;
    url?: string;
}

interface ButtonState {}

/** Button.
 * Props:
 * - text: Text in the button
 * - handleClick: Optional callback for clicking on button
 * - url: Optional href url for clicking on button
 */
class Button extends React.Component<ButtonProps, ButtonState> {

    constructor(props: any) {
        super(props);
    }

    render() : React.ReactNode {
        let button: React.ReactNode = null;

        if (this.props.url != null) {
            button = (
                <a href={this.props.url} > 

                    <div 
                        className="hover-zoom-button" 
                        style={{width: "100%", fontSize: "1.5em", textAlign: "center", margin: "10px"}}>

                        {this.props.text}
                        
                    </div>

                </a>
            );
        } else if (this.props.handleClick != null) {
            button = (
                <div 
                    className="hover-zoom-button" 
                    style={{width: "100%", fontSize: "1.5em", textAlign: "center", margin: "10px"}}
                    onClick={this.props.handleClick}>
    
                    {this.props.text}
    
                </div>
            );
        } else {
            button = (
                <div 
                    className="hover-zoom-button" 
                    style={{width: "100%", fontSize: "1em", textAlign: "center", margin: "10px"}}>
    
                    {this.props.text}
    
                </div>
            );
        }
        return ( button );
    }
}

export default Button;