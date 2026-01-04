import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Box,
  Typography,
} from "@mui/material";
import { useTheme, useMediaQuery } from "@mui/material";
import { useNavigate } from "react-router-dom";

type SearchResultTableRow = {
  id: number;
  data: {
    toShow: React.ReactNode;
    route?: string;
  }[];
};

export type SearchResultTableProps = {
  columnNames: string[];
  rows: SearchResultTableRow[];
};

const SearchResultTable = (props: SearchResultTableProps) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));
  const navigate = useNavigate();

  return (
    <Box sx={{ display: "flex", margin: "auto", width: "100%", marginTop: 8 }}>
      {!isMobile ? (
        <TableContainer
          component={Paper}
          sx={{ boxShadow: 4, borderRadius: 3, overflow: "hidden" }}
        >
          <Table>
            <TableHead>
              <TableRow sx={{ backgroundColor: "#04315fff" }}>
                {props.columnNames.map((header, index) => (
                  <TableCell
                    key={index}
                    align={"center"}
                    sx={{
                      fontWeight: "bold",
                      color: "white",
                      textTransform: "uppercase",
                      letterSpacing: "0.05em",
                      fontSize: "0.9rem",
                    }}
                  >
                    {header}
                  </TableCell>
                ))}
              </TableRow>
            </TableHead>
            <TableBody>
              {props.rows.map((r) => (
                <TableRow
                  key={r.id}
                  sx={{
                    "&:last-child td, &:last-child th": { border: 0 },
                    "&:nth-of-type(odd)": { backgroundColor: "#f9f9f9" },
                    "&:hover": {
                      backgroundColor: "#e3f2fd",
                      transform: "scale(1.01)",
                      transition: "0.2s ease-in-out",
                    },
                  }}
                >
                  {r.data.map((cell, cellIndex) => (
                    <TableCell
                      key={cellIndex}
                      align="center"
                      onClick={() => {
                        if (cell.route) {
                          navigate(cell.route);
                        }
                      }}
                      sx={{
                        cursor: cell.route ? "pointer" : "default",
                        "&:hover": cell.route
                          ? {
                              textDecoration: "underline",
                            }
                          : {},
                      }}
                    >
                      {cell.toShow}
                    </TableCell>
                  ))}
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <>
          <Box sx={{ width: "100%", display: "grid", gap: 2 }}>
            {props.rows.map((r) => (
              <Paper
                key={r.id}
                sx={{
                  p: 2,
                  borderRadius: 2,
                  boxShadow: 3,
                  "&:hover": { backgroundColor: "#f9f9f9" },
                }}
              >
                {props.columnNames.map((c, index) => (
                  <Typography
                    onClick={() => {
                      if (r.data[index].route) navigate(r.data[index].route);
                    }}
                    variant="body2"
                    sx={{
                      cursor: r.data[index].route ? "pointer" : "default",
                      "&:hover": r.data[index].route
                        ? {
                            textDecoration: "underline",
                          }
                        : {},
                    }}
                  >
                    <strong>{c}:</strong> {r.data[index].toShow}
                  </Typography>
                ))}
              </Paper>
            ))}
          </Box>
        </>
      )}
    </Box>
  );
};

export default SearchResultTable;
