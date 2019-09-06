import React from "react";

import "./Avatar.css";

import codacyLogo from "../../codacy-white.svg";
const Avatar = ({ url, name }) => {
  if (url) {
    return (
      <img
        className="avatar"
        src={url || codacyLogo}
        alt={name}
        lazy="true"
      ></img>
    );
  } else {
    return (
      <div className="no-image-avatar">
        <span>{name[0]}</span>
      </div>
    );
  }
};

export default Avatar;
