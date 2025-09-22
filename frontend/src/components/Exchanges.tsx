import { Table, TableBody, TableCell, TableContainer, TableHead, 
  TableRow, Paper, Box, Typography} from '@mui/material'
import { useTheme, useMediaQuery } from '@mui/material'

export type ExchangesProps = {
  exchanges: {
    id: number;
    timeRange: {
      startedAt: string;
      endAt: string;
    };
    university: {
      id: number;
      name: string;
    };
    universityMajorName: string;
    city:{
      name: string;
      countryName: string;
    };
  }[]
}

const Exchanges = (props: ExchangesProps) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

  return (
    <Box sx={{display: 'flex', margin: 'auto', width: '91%'}}>
      {!isMobile ? (
        <TableContainer component={Paper}
          sx={{boxShadow: 4, borderRadius: 3, overflow: 'hidden'}}>
          <Table>
            <TableHead>
              <TableRow sx={{backgroundColor: '#04315fff'}}>
                {['University', 'Major', 'City', 'Started at', 'End at'].map(
                  (header, index) => (
                    <TableCell key={index} align={'center'}
                      sx={{fontWeight: 'bold', color: 'white', textTransform: 'uppercase',
                        letterSpacing: '0.05em', fontSize: '0.9rem'}}>
                      {header}
                    </TableCell>
                  )
                )}
              </TableRow>
            </TableHead>
            <TableBody>
              {props.exchanges.map((e) => (
                <TableRow key={e.id}
                  sx={{
                    '&:last-child td, &:last-child th': {border: 0},
                    '&:nth-of-type(odd)': {backgroundColor: '#f9f9f9'},
                    '&:hover': {backgroundColor: '#e3f2fd',
                      transform: 'scale(1.01)', transition: '0.2s ease-in-out'}}}>
                  <TableCell align='center'>
                    <Typography fontWeight='600'>{e.university.name}</Typography>
                  </TableCell>
                  <TableCell align='center'>{e.universityMajorName}</TableCell>
                  <TableCell align='center'>{e.city.name}</TableCell>
                  <TableCell align='center'>{e.timeRange.startedAt}</TableCell>
                  <TableCell align='center'>{e.timeRange.endAt}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Box sx={{width: '100%', display: 'grid', gap: 2}}>
          {props.exchanges.map((e) => (
            <Paper key={e.id}
              sx={{p: 2, borderRadius: 2, boxShadow: 3,
                '&:hover': { backgroundColor: '#f9f9f9'}}}>
              <Typography variant='h6' fontWeight='600' gutterBottom>
                {e.university.name}
              </Typography>
              <Typography variant='body2'>
                <strong>Major:</strong> {e.universityMajorName}
              </Typography>
              <Typography variant='body2'>
                <strong>City:</strong> {e.city.name}
              </Typography>
              <Typography variant='body2'>
                <strong>Started:</strong> {e.timeRange.startedAt}
              </Typography>
              <Typography variant='body2'>
                <strong>Ended:</strong> {e.timeRange.endAt}
              </Typography>
            </Paper>
          ))}
        </Box>
      )}
    </Box>
  );
}

export default Exchanges