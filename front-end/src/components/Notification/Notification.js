import React from "react";
import Toast from "react-bootstrap/Toast";

const Notification = ({
  title,
  titleSmall = "",
  message,
  handleClose,
  show,
  delay = 3000
}) => {
  return (
    <div
      aria-live="polite"
      aria-atomic="true"
      style={{
        position: "relative",
        minHeight: "200px"
      }}
    >
      <div
        style={{
          position: "fixed",
          bottom: "2em",
          right: "2em"
        }}
      >
        <Toast onClose={handleClose} show={show} delay={delay} autohide>
          <Toast.Header>
            <strong className="mr-auto">{title}</strong>
            <small>{titleSmall}</small>
          </Toast.Header>
          <Toast.Body className="text-danger">{message}</Toast.Body>
        </Toast>
      </div>
    </div>
  );
};

export default Notification;
