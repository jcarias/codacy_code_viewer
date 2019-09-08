import React from "react";

import "./Avatar.css";
import codacyLogo from "../../codacy-white.svg";

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
