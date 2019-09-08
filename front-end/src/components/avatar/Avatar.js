import React from "react";

import "./Avatar.css";
import codacyLogo from "../../codacy-white.svg";

const computeColorsForCode = charCode => {
  let colors = {
    foreGround: "black",
    backGround: "white"
  };
  if (charCode) {
    let codeOffset = charCode >= 65 && charCode <= 90 ? 64 : 96;
    let hueValue = (360 / 26) * (charCode - codeOffset);
    console.log(charCode, hueValue, colors);
    colors.backGround = `hsl(${hueValue},100%,50%)`;
    colors.foreGround = charCode - codeOffset <= 13 ? "black" : "white";
  }
  return colors;
};

const Avatar = ({ url, name }) => {
  function getAvatarImage(url, name) {
    if (url) {
      return (
        <img
          className="avatar"
          src={url || codacyLogo}
          alt={""}
          lazy="true"
        ></img>
      );
    } else {
      return (
        <div className="no-image-avatar">
          <span>{name.charAt(0)}</span>
        </div>
      );
    }
  }

  return (
    <div className="avatar-container">
      <div>{getAvatarImage(url, name)}</div>
      <div className="ellipsis name-container">
        <span>{name}</span>
      </div>
    </div>
  );
};

export default Avatar;
