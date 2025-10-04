import PanelHeader from '../components/PanelHeader'
import { Box, Button, Paper, Table, TableBody, TableCell, 
  TableContainer, TableHead, TableRow, Typography, useMediaQuery, 
  useTheme } from '@mui/material'
import { sendGetUserExchangesRequest } from '../utils/user'
import { Alert } from '@mui/material'
import { useState } from 'react'
import NoContent from '../components/NoContent'
import { useEffect } from 'react'
import DeleteIcon from '@mui/icons-material/Delete'
import { sendDeleteExchangeRequest } from '../utils/exchange'
import type { AlertMessage } from '../types/AlertMessage'

type ActionExchangeTableProps = {
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
  }[],
  message: AlertMessage | null,
  setMessage: (message: AlertMessage) => void;
  setDeletedExchangeId: (exchangeId: number) => void;
}

type ManageExchangesPanelProps = {
  userId: number | string;
}

const ActionExchangeTableProps = (props: ActionExchangeTableProps) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

  const handleDeletion = async (exchangeId: number) => {
    props.setMessage({type: 'info', content: 'Waiting for server response.'})
    const response = await sendDeleteExchangeRequest(exchangeId)
    if (response.isSuccess) {
      props.setMessage({type: 'success', content: 'Exchange was deleted successfully.'})
      props.setDeletedExchangeId(exchangeId)
    } else {
      props.setMessage({type: 'error', content: 'Failed to delete exchange.'})
    }
  }

  return (
    <Box sx={{display: 'flex', margin: 'auto', width: '91%'}}>
      {!isMobile ? (
        <TableContainer component={Paper}
          sx={{boxShadow: 4, borderRadius: 3, overflow: 'hidden'}}>
          <Table>
            <TableHead>
              <TableRow sx={{backgroundColor: '#04315fff'}}>
                {['University', 'Major', 'City', 'Started at', 'End at' , ''].map(
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
                  <TableCell align='center'>
                    {e.city.name} 
                    <img src={`/flags/${e.city.countryName}.png`} alt={`${e.city.countryName} flag`} 
                      style={{height: '0.8rem', marginLeft: 2}}/>
                  </TableCell>
                  <TableCell align='center'>{e.timeRange.startedAt}</TableCell>
                  <TableCell align='center'>{e.timeRange.endAt}</TableCell>
                  <TableCell align='center'>
                    <Button variant="contained" color="error" size="small"
                      startIcon={<DeleteIcon/>}
                      onClick={() => {
                        handleDeletion(e.id)
                      }}>
                      Delete
                    </Button>
                  </TableCell>
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
                <img src={`/flags/${e.city.countryName}.png`} alt={`${e.city.countryName} flag`} 
                  style={{height: '0.8rem', marginLeft: 2}}/>
              </Typography>
              <Typography variant='body2'>
                <strong>Started:</strong> {e.timeRange.startedAt}
              </Typography>
              <Typography variant='body2'>
                <strong>Ended:</strong> {e.timeRange.endAt}
              </Typography>
              <Button sx={{marginTop: 1}} variant="contained" color="error" 
                size="small" startIcon={<DeleteIcon/>} 
                onClick={() => {
                  handleDeletion(e.id)
                }}>
                Delete
              </Button>
            </Paper>
          ))}
        </Box>
      )}
    </Box>
  );
}

const ManageExchangesPanel = (props: ManageExchangesPanelProps) => {
  const [exchangesProps, setExchangesProps] = useState<ActionExchangeTableProps | null>(null)
  const [message, setMessage] = useState<AlertMessage | null>(null)
  const [deletedExchangeId, setDeletedExchangeId] = useState<number | null>(null)

  const getExchanges = async () => {
    const result = await sendGetUserExchangesRequest(props.userId)
    if (result.isSuccess) {
      const props: ActionExchangeTableProps = {
        message: message,
        setMessage: setMessage,
        setDeletedExchangeId: setDeletedExchangeId,
        exchanges: result.data.map(e => ({
          id: e.id,
          timeRange: {
            startedAt: e.timeRange.startedAt,
            endAt: e.timeRange.endAt
          },
          university: {
            id: e.university.id,
            name: e.university.englishName
              ? e.university.englishName
              : e.university.nativeName
          },
          universityMajorName: e.universityMajor.name,
          city: {
            name: e.city.name,
            countryName: e.city.country.name
          }
        }))
      }
      setExchangesProps(props)
    }
  }

  useEffect(() => {
    if (!exchangesProps) {
      getExchanges()
    } else {
      setExchangesProps({
        exchanges: exchangesProps.exchanges.filter(e => e.id !== deletedExchangeId),
        message: message,
        setMessage: setMessage,
        setDeletedExchangeId: setDeletedExchangeId
      })
    }
  }, [deletedExchangeId])

  return (
      <Box>
        <PanelHeader label='Manage existing exchanges'/>
        {!message &&
          <br/>
        }
        {message && 
          <Alert variant='filled' severity={message.type} sx={{marginY: 2}}>
            {message.content}
          </Alert>
        }
        {exchangesProps && (exchangesProps.exchanges.length !== 0) ? 
          <ActionExchangeTableProps {...exchangesProps}/> :
          <NoContent title='No exchanges yet' subheader="You haven't add any exchange."/>
        }
      </Box>
    )
}

export default ManageExchangesPanel