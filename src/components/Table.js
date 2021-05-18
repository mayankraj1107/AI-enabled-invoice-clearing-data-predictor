import React, { useState } from "react";
import axios from "axios";
import {
  CircularProgress,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from "@material-ui/core";


import InfiniteScroll from "react-infinite-scroll-component";

import { blue } from "@material-ui/core/colors";

function Table(props) {
  let [isNext, isNextFunc] = React.useState(false);
  let [pageCount, setCount] = React.useState(0);
  
  const url = `http://localhost:8080/1805301/TABLE?page=${pageCount}&limit=10%60`;
  const [responseData, setResponseData] = useState([]);
  const loadMoreData = () => {
    setCount(pageCount + 1);
  };
  console.log(pageCount);
  function handlerowcolor(props) {
    if (props)
      return {
        background: "#2A5368 0% 0% no-repeat padding-box",
        borderRadius: "4px",
        opacity: "1",
      };
  }
  React.useEffect(() => {
    axios
      .get(url)
      .then((response) => {
        setResponseData([...responseData, ...response.data]);
        isNextFunc(true);
      })
      .catch((error) => {
        console.log(error);
      });
    setResponseData(
      responseData.map((response) => {
        return {
          select: false,
          background: blue,
          id: response.doc_id,
          name_customer: response.name_customer,
          cust_number: response.cust_number,
          invoice_id: response.invoice_id,
          total_open_amount: response.total_open_amount,
          due_in_date: response.due_in_date,
          notes: response.notes,
        };
      })
    );
  }, [pageCount]);
  console.log("After axios ", pageCount);
  const TableCellstyle = {
    width: "400px",
    top: "309px",
    left: "121px",
    height: "23px",
    textAlign: "center",
    font: "normal normal normal 20px/24px Ubuntu",
    letterSpacing: "0px",
    color: "#FFFFFF",
    opacity: "1",
    border: "0px",
  };
  return (
    <div
      id="scrollableDiv"
      style={{
        overflow: `scroll`,
        display: `flex`,
        height: `100vh`,
        // width: `100%`,
      }}
    >
      <InfiniteScroll
        dataLength={responseData.length}
        next={loadMoreData}
        hasMore={isNext}
        // style={
        //   {
        //     display:"flex",
        //     width: 100%
        //   }
        //}
        loader={
          <div
            style={{
              height: "80%",
              paddingLeft: "35%",
              overflow: "hidden",
              // display: "flex",
              width: "100%",
            }}
          >
            <CircularProgress />
          </div>
        }
        scrollableTarget="scrollableDiv"
      >
        <TableHead>
          <TableRow>
            <th scope="col">
              {/* <TableCell class=""> */}
              <TableCell style={TableCellstyle}>
                <input
                  type="checkbox"
                  onChange={(e) => {
                    var checked = e.target.checked;
                    var checked2 = e.currentTarget.checked2;
                    setResponseData(
                      responseData.map((response) => {
                        if (checked == true) response.select = true;
                        else response.select = false;
                        response.background = blue;
                        return response;
                      })
                    );
                  }}
                ></input>
              </TableCell>
            </th>
            {/* </TableCell> */}
            <TableCell class="head" display="flex">
              Customer Name
            </TableCell>
            <TableCell class="head" scope="col">
              Customer No
            </TableCell>
            <TableCell class="head" scope="col">
              Order_Id
            </TableCell>
            <TableCell class="head" scope="col">
              Order_amount
            </TableCell>
            <TableCell class="head" scope="col">
              Due Date
            </TableCell>
            <TableCell class="head" scope="col">
              Predicted payment date
            </TableCell>
            <TableCell class="head" scope="col">
              Predicted aging bucket
            </TableCell>
            <TableCell class="head" scope="col">
              Notes
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {responseData.map((response, i) => (
            <TableRow
              id={response.doc_id}
              style={handlerowcolor(response.select)}
            >
              {/* <TableCell class=" check" > */}
              <th scope="row">
                <TableCell style={TableCellstyle}>
                  <input
                    onChange={(event) => {
                      var checked = event.target.checked;
                      setResponseData(
                        responseData.map((data) => {
                          if (response.doc_id == data.doc_id) {
                            data.select = checked;
                          }
                          // alert(response.id);
                          // console.log(response.doc_id);
                          // var z = response.doc_id;
                          props.setDoc({
                            dd: response.doc_id,
                          });
                          props.setDelete([...props.d, response.doc_id]);

                          return data;
                        })
                      );
                    }}
                    type="checkbox"
                    checked={response.select}
                  ></input>
                </TableCell>
              </th>
              {/* </TableCell> */}
              <TableCell class="font name" style={TableCellstyle}>
                {response.name_customer}
              </TableCell>
              <TableCell class="font">{response.cust_number}</TableCell>
              <TableCell class="font">{response.invoice_id} </TableCell>
              <TableCell class="font">
                {Math.round(
                  (response.total_open_amount / 1000 + Number.EPSILON) * 100
                ) / 100}
                K
              </TableCell>
              <TableCell class="font due">
                {response.due_in_date}</TableCell>
              <TableCell class="font predict">--</TableCell>
              <TableCell class="font predict">--</TableCell>
              <TableCell class="font notes">{response.notes}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </InfiniteScroll>
    </div>
  );
}
export default Table;
