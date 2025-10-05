import { useEffect, useState } from 'react'
import Box from '@mui/material/Box'
import type { ReactElement } from 'react'
import { Radio, RadioGroup } from '@mui/material'
import { FormControl, Button } from '@mui/material'
import { sendUpdateStatusRequest } from '../utils/user'
import Alert from '@mui/material/Alert'
import { sendGetUserStatusesRequest } from '../utils/user-status'
import { FormControlLabel } from '@mui/material'
import type { AlertMessage } from '../types/AlertMessage'
import type { GetUserStatusResponse } from '../dtos/user-status/GetUserStatusResponse'
import PanelHeader from '../components/PanelHeader'

type UserStatus = GetUserStatusResponse

const UpdateUserStatusPanel = () => {
  const [statuses, setStatuses] = useState<UserStatus[]>([])
  const [selectedStatusId, setSelectedStatusId] = useState<number | null>(null)
  const [message, setMessage] = useState<AlertMessage | null>(null)

  const getStatuses = async () => {
    const result = await sendGetUserStatusesRequest()
    if (result.isSuccess) {
      const allStatuses = result.data
      allStatuses.push({id: 0, name: 'UNKNOWN'})
      setStatuses(allStatuses)
    }
  }

  useEffect(() => {
    getStatuses()
  }, [])

  useEffect(() => {
    setMessage(null)
  }, [selectedStatusId])

  const handleStatusUpdate = async () => {
    const statusId = selectedStatusId !== 0 ? selectedStatusId : null
    setMessage({type: 'info', content: 'Waiting for server response.'})
    const result = await sendUpdateStatusRequest({statusId: statusId})
    if (result.isSuccess) {
      setMessage({type: 'success', content: 'Status was updated successfully.'})
    } else {
      setMessage({type: 'error', content: 'Failed to update status. Please try again later.'})
    }
  }

  const getInteractionElement = (statusId: number): ReactElement => {
    if ((selectedStatusId === statusId) && (message === null)) {
      return (
        <Button variant='contained' size='small' sx={{marginLeft: 2}}
          onClick={handleStatusUpdate}>
          CONFIRM
        </Button>
      )
    }
    return <></>
  }

  return (
    <Box>
      <PanelHeader label='Assign status that fits you the most'/>
      {message != null ?
        <Alert variant='filled' severity={message.type} sx={{marginY: 2}}>
          {message.content}
        </Alert> : 
        <></>
      }
      <FormControl>
        <RadioGroup name='statuses-buttons-group'
          value={selectedStatusId}
          onChange={(event) => {
            setSelectedStatusId(Number(event.target.value))
          }}>
          {statuses.map(s => (
            <FormControlLabel key={s.id} value={s.id} control={<Radio />} label={(
              <>
                {s.name}
                {getInteractionElement(s.id)}
              </>)}
          />))}
        </RadioGroup>
      </FormControl>
    </Box>
  )
}

export default UpdateUserStatusPanel