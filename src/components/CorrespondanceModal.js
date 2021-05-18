import React from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import { makeStyles } from "@material-ui/core";
import CloseIcon from "@material-ui/icons/Close";
import ViewTable from "./ViewTable";
const useStyles = makeStyles((theme) => ({
  root: {
    "& > *": {
      margin: theme.spacing(1),
    },
  },
  dgb: {
    background: "#2A3F4D",
    color: "#FFFFFF",
    width: "90vw",
  },
  closeIcon: {
    float: "right",
    color: "#FFFFFF",
    opacity: 0.5,
  },
  rightButton: {
    color: "#FFFFFF",
    borderColor: "#14AFF1",
    marginRight: "10px",
    borderRadius: "10px",
    float: "right",
    height: "6vh",
    width: "6.5vw",
    fontSize: "0.8rem",
    textTransform: "none",
  },
  downloadButton: {
    color: "#FFFFFF",
    background: "#14AFF1",
    borderColor: "#14AFF1",
    marginRight: "10px",
    borderRadius: "10px",
    float: "right",
    height: "6vh",
    width: "7.5vw",
    fontSize: "0.8rem",
    textTransform: "none",
  },
}));
export default function ViewModal(props) {
  const classes = useStyles();
  const { viewPopup, setViewPopup } = props;
  const handleClose = () => {
    setViewPopup(false);
  };
  return (
    <Dialog open={viewPopup} onClose={handleClose} maxWidth={300}>
      <DialogTitle className={classes.dgb}>
        View Correspondence (2)
        <Button
          onClick={handleClose}
          color="primary"
          className={classes.closeIcon}
        >
          {" "}
          <CloseIcon />
        </Button>
      </DialogTitle>
      <DialogContent className={classes.dgb} dividers={true}>
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>Subject: </font>
          <font style={{ fontSize: "2.5vh" }}>
            Invoice Details - (Account Name)
          </font>{" "}
        </div>
        <br />
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            Dear Sir/Madam,{" "}
          </font>
          <br />
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>Greetings!</font>
        </div>
        <br />
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            This is to remind you that there are one or more open invoices on
            your account. lease provide at your earliest convenience an update
            on the payment details or clarify the reason for the delay. If you
            have any specific issue with the invoice(s), please let us know so
            that we can address it to the correct Department.
          </font>
        </div>
        <br />
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            Please find the details of the invoices below:
          </font>
        </div>
        <br />
        <div>
          <ViewTable />
        </div>
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            Total Amount to be Paid:{" "}
          </font>
          <font style={{ fontSize: "2.5vh" }}> $124.00K</font>{" "}
        </div>
        <br />
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            In case you have already made a payment for the above items, please
            send us the details to ensure the payment is posted.
          </font>
          <br />
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            Let us know if we can be of any further assistance. Looking forward
            to hearing from you.
          </font>
        </div>
        <br />
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            Kind Regards,{" "}
          </font>
        </div>
        <br />
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            [Sender’s First Name]
          </font>
        </div>
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            [Sender’s Last Name]
          </font>
        </div>
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            Phone : [Sender’s contact number]
          </font>
        </div>
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            Fax : [If any]{" "}
          </font>
        </div>
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            {" "}
            Email : [Sender’s Email Address]
          </font>
        </div>
        <div>
          <font style={{ opacity: 0.7, fontSize: "2.5vh" }}>
            Company Name[Sender’s Company Name]
          </font>
        </div>
      </DialogContent>
      <DialogActions style={{ background: "#2A3F4D" }}>
        <Button
          variant="outlined"
          color="primary"
          className={classes.rightButton}
          onClick={handleClose}
        >
          Cancel
        </Button>
        <Button
          variant="outlined"
          color="primary"
          className={classes.downloadButton}
          onClick={handleClose}
        >
          Download
        </Button>
      </DialogActions>
    </Dialog>
  );
}
