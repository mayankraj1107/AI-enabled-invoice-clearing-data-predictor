import React from "react";
import { withStyles, makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import MuiDialogTitle from "@material-ui/core/DialogTitle";
import MuiDialogContent from "@material-ui/core/DialogContent";
import MuiDialogActions from "@material-ui/core/DialogActions";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import Typography from "@material-ui/core/Typography";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import "./Butt_Style.css";
const styles = (theme) => ({
  root: {
    margin: 0,
    padding: theme.spacing(2),
  },
  closeButton: {
    position: "absolute",
    right: theme.spacing(1),
    top: theme.spacing(1),
    color: theme.palette.grey[500],
  },
});

const DialogTitle = withStyles(styles)((props) => {
  const { children, classes, onClose, ...other } = props;
  return (
    <MuiDialogTitle disableTypography className={classes.root} {...other}>
      <Typography variant="h6">{children}</Typography>
      {onClose ? (
        <IconButton
          aria-label="close"
          className={classes.closeButton}
          onClick={onClose}
        >
          <CloseIcon />
        </IconButton>
      ) : null}
    </MuiDialogTitle>
  );
});

const DialogContent = withStyles((theme) => ({
  root: {
    padding: theme.spacing(2),
  },
}))(MuiDialogContent);

const DialogActions = withStyles((theme) => ({
  root: {
    margin: 0,
    padding: theme.spacing(1),
  },
}))(MuiDialogActions);

export default function ViewCorrespondance() {
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      {/* <Button variant="outlined" color="primary" onClick={handleClickOpen}>
                <div class="inner">Correspondence</div>
            </Button> */}
      <Button
        class="viewcorrespondancebutton"
        variant="outlined"
        onClick={handleClickOpen}
      >
        View Correspondance
      </Button>
      <Dialog
        onClose={handleClose}
        aria-labelledby="customized-dialog-title"
        open={open}
        maxWidth="xl"
      >
        <DialogTitle
          id="customized-dialog-title"
          onClose={handleClose}
          className="dialog"
        >
          <div className="add">View Correspondence</div>
        </DialogTitle>
        <DialogContent dividers class="typo">
          <Typography gutterBottom >
            Subject: Invoice Details - Dear Sir/Madam, Greetings! This is to
            remind you that there are one or more open invoices on your account.
            lease provide at your earliest convenience an update on the payment
            details or clarify the reason for the delay. If you have any
            specific issue with the invoice(s), please let us know so that we
            can address it to the correct Department. Please find the details of
            the invoices below:
          </Typography>
          <BasicTable />
          {/* <Typography gutterBottom>
            Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis
            lacus vel augue laoreet rutrum faucibus dolor auctor.
          </Typography>
          <Typography gutterBottom>
            Aenean lacinia bibendum nulla sed consectetur. Praesent commodo cursus magna, vel
            scelerisque nisl consectetur et. Donec sed odio dui. Donec ullamcorper nulla non metus
            auctor fringilla.
          </Typography> */}
        </DialogContent>
        <DialogActions className="button">
          <Button
            onClick={handleClose}
            color="primary"
            variant="outlined"
            class="clevu"
          >
            <div className="add1">Cancel</div>
          </Button>
          <Button
            onClick={handleClose}
            color="primary"
            variant="outlined"
            class="down"
          >
            <div className="add1">Download</div>
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
export function BasicTable() {
  const classes = TableStyles();

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="simple table">
        <TableHead className={classes.tablehead}>
          <TableRow>
            <TableCell className={classes.tablehead}>Invoie Number</TableCell>
            <TableCell align="right" className={classes.tablehead}>
              PO Number
            </TableCell>
            <TableCell align="right" className={classes.tablehead}>
              Invoice Date
            </TableCell>
            <TableCell align="right" className={classes.tablehead}>
              Due date
            </TableCell>
            <TableCell align="right" className={classes.tablehead}>
              Order Currency
            </TableCell>
            <TableCell align="right" className={classes.tablehead}>
              Open Amount
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row, index) => (
            <TableRow
              key={row.invoice_number}
              className={index % 2 ? classes.lightRow : classes.darkRow}
            >
              <TableCell
                className={classes.tablebody}
                component="th"
                scope="row"
              >
                {row.invoice_number}
              </TableCell>
              <TableCell className={classes.tablebody} align="right">
                {row.po_number}
              </TableCell>
              <TableCell className={classes.tablebody} align="right">
                {row.invoice_date}
              </TableCell>
              <TableCell className={classes.tablebody} align="right">
                {row.due_date}
              </TableCell>
              <TableCell className={classes.tablebody} align="right">
                {row.currency}
              </TableCell>
              <TableCell className={classes.tablebody} align="right">
                {row.open_amount}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
const TableStyles = makeStyles({
  table: {
    background: "#2A3E4C",
    borderColor: "black",
  },
  tablehead: {
    color: "#97A1A9 !important",
    border: "0",
  },
  tablebody: {
    color: "white !important",
    border: "0",
  },
  lightRow: {
    background: "#283A46",
  },
  darkRow: {},
});

function createData(
  invoice_number,
  po_number,
  invoice_date,
  due_date,
  currency,
  open_amount
) {
  return {
    invoice_number,
    po_number,
    invoice_date,
    due_date,
    currency,
    open_amount,
  };
}

const rows = [
  createData(1023, 456, 789, 456, 123, 111),
  createData(1323, 456, 789, 456, 123, 111),
];
