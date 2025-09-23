import Navbar from '../components/Navbar'
import { useEffect, useState } from 'react'
import Box from '@mui/material/Box'
import List from '@mui/material/List'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import Typography from '@mui/material/Typography'
import PersonIcon from '@mui/icons-material/Person'
import SecurityIcon from '@mui/icons-material/Security'
import NotificationsIcon from '@mui/icons-material/Notifications';
import type { ReactElement } from 'react'
import { Collapse, FormControlLabel, Radio, RadioGroup } from '@mui/material'
import { ExpandLess, ExpandMore } from '@mui/icons-material'
import { Fragment } from 'react'
import { sendGetCountriesRequest } from '../utils/country'
import { FormControl, Button } from '@mui/material'
import { sendAssignCountryOfOriginRequest } from '../utils/user'
import Alert from '@mui/material/Alert'

type Country = {
  id: number;
  name: string;
}

type Message = {
  type: 'success' | 'info' | 'error';
  content: string;
}

type SectionName = 
  'Informations' |
  'Exchanges' |
  'Security'

type SubsectionName = 
  'Home university' |
  'Country of origin' |
  'Status' |
  'Description'

type Section = {
  name: SectionName;
  subsections: SubsectionName[];
  icon: ReactElement<any, any>
}

type SectionListProps = {
  selectedSectionName: SectionName | null;
  setSelectedSectionName: (value: SectionName | null) => void;
  selectedSubsectionName: SubsectionName | null;
  setSelectedSubsectionName: (value: SubsectionName | null) => void;
}

const SectionsList = (props: SectionListProps) => {
  const sections: Section[] = [
    {name: 'Informations', 
      subsections: [
        'Home university', 
        'Country of origin', 
        'Status', 
        'Description'], 
      icon: (<PersonIcon/>)},
    {name: 'Exchanges', 
      subsections: [], 
      icon: (<SecurityIcon/>)},
    {name: 'Security', 
      subsections: [], 
      icon: (<NotificationsIcon/>)},
  ];

  const handleSectionClick = (section: Section) => {
    if (props.selectedSectionName === section.name) {
      props.setSelectedSectionName(null);
    } else {
      props.setSelectedSectionName(section.name);
    }
  }

  const handleSubsectionClick = (subsection: SubsectionName) => {
    if (props.selectedSubsectionName === subsection) {
      props.setSelectedSubsectionName(null)
    } else {
      props.setSelectedSubsectionName(subsection)
    }
  }

  return (
    <Box sx={{width: {xs: '100vw', sm: '30%', lg: '20%'}, backgroundColor: '#f9f9f9', 
      borderRight: '1px solid #e0e0e0', display: 'flex', flexDirection: 'column'}}>
      <Typography variant='h6'
        sx={{padding: {xs: 1.5, sm: 2}, fontSize: {xs: '1rem', sm: '1.25rem'},
          color: 'text.secondary', fontWeight: 600}}>
        Edit
      </Typography>
      <List component='nav' sx={{paddingTop: 0}}>
        {sections.map((section) => (
          <Fragment key={section.name}>
            <ListItemButton
              selected={props.selectedSectionName === section.name}
              onClick={() => handleSectionClick(section)}
              sx={{minHeight: 48,
                '&.Mui-selected': {backgroundColor: 'rgba(0, 0, 0, 0.04)',
                  borderLeft: '4px solid #1976d2', color: '#1976d2'},
                '&.Mui-selected .MuiListItemIcon-root': {color: '#1976d2'}}}>
              <ListItemIcon sx={{ minWidth: 40 }}>{section.icon}</ListItemIcon>
              <ListItemText
                primaryTypographyProps={{fontSize: {xs: '0.9rem', sm: '1rem'}}}
                primary={section.name}
              />
              {props.selectedSectionName === section.name ? 
                <ExpandLess/> : 
                <ExpandMore/>
              }
            </ListItemButton>
            <Collapse in={props.selectedSectionName === section.name} 
              timeout='auto' unmountOnExit>
              <List component='div' disablePadding>
                {section.subsections.map((ss) => (
                  <ListItemButton
                    key={ss}
                    selected={props.selectedSubsectionName === ss}
                    onClick={() => handleSubsectionClick(ss)}
                    sx={{pl: {xs: 3, sm: 4},
                      minHeight: 44,
                      '&.Mui-selected': {
                        backgroundColor: 'rgba(0, 0, 0, 0.04)',
                        borderLeft: '4px solid #207ddaff',
                        color: '#207ddaff',
                      },
                      '&.Mui-selected .MuiListItemIcon-root': {color: '#207ddaff'},
                    }}
                  >
                    <ListItemText
                      primaryTypographyProps={{fontSize: {xs: '0.85rem', sm: '0.95rem'}}}
                      primary={ss}
                    />
                  </ListItemButton>
                ))}
              </List>
            </Collapse>
          </Fragment>
        ))}
      </List>
    </Box>
  );
};

const PanelHeader = ({label}: {label: string}) => {
  return (
    <Typography variant='h5' sx={{fontWeight: 600, fontSize: {xs: '1.25rem', md: '1.75rem'},
      color: 'text.primary', letterSpacing: 0.3}}>
      {label}
    </Typography>
  )
}

const AssignCountryOfOriginPanel = () => {
  const [countries, setCountries] = useState<Country[]>([])
  const [selectedCountryId, setSelectedCountryId] = useState<number | null>(null)
  const [message, setMessage] = useState<Message | null>(null)

  const getCountries = async () => {
    const result = await sendGetCountriesRequest()
    if (result.isSuccess) {
      const allCountries: Country[] = result.data
        .map(c => ({id: c.id, name: c.englishName}))
      allCountries.push({id: 0, name: 'no country'})
      setCountries(allCountries)
    }
  }

  const handleCountryAssignment = async () => {
    const countryId = selectedCountryId !== 0 ? selectedCountryId : null
    setMessage({type: 'info', content: 'Waiting for response'})
    const result = await sendAssignCountryOfOriginRequest({countryId: countryId})
    if (result.isSuccess) {
      setMessage({type: 'success', content: 'Success'})
    } else {
      setMessage({type: 'error', content: 'An error occured'})
    }
  }

  const getInteractionElement = (countryId: number): ReactElement => {
    if ((selectedCountryId === countryId) && (message === null)) {
      return (
        <Button variant='contained' size='small' sx={{marginLeft: 2}}
          onClick={handleCountryAssignment}>
          CONFIRM
        </Button>
      )
    }
    return <></>
  }

  useEffect(() => {
    getCountries()
  }, [])

  useEffect(() => {
    setMessage(null)
  }, [selectedCountryId])

  return (
    <Box>
      <PanelHeader label='Assign country of origin to yourself'/>
      {message != null ?
        <Alert variant='filled' severity={message.type} sx={{marginY: 2}}>
          {message.content}
        </Alert> : 
        <></>
      }
      <FormControl>
        <RadioGroup name='countries-buttons-group'
          value={selectedCountryId}
          onChange={(event) => {
            setSelectedCountryId(Number(event.target.value))
          }}>
          {countries.map(c => (
            <FormControlLabel key={c.id} value={c.id} control={<Radio />} label={(
              <>
                {c.name}
                {c.id !== 0 ?
                  <img src={`/flags/${c.name}.png`} 
                    style={{height: '0.8rem', marginLeft: 3}}/> : 
                  <></>
                }
                {getInteractionElement(c.id)}
              </>)}
          />))}
        </RadioGroup>
      </FormControl>
    </Box>
  )
}

const EditUserPage = () => {
  const [selectedSectionName, setSelectedSectionName] = useState<SectionName | null>(null)
  const [getSubsectionPanel, setSelectedSubsectionName] = useState<SubsectionName | null>(null)

  const getPanel = () => {
    if (getSubsectionPanel === 'Country of origin') {
      return <AssignCountryOfOriginPanel/>
    }
    return <></>
  }

  return (
    <>
      <Box sx={{display: 'flex', height: '100vh', flexDirection: 'column'}}>
        <Navbar/>
        <Box sx={{display: 'flex', flexGrow: {sm: 1}, 
          flexDirection: { xs: 'column', sm: 'row'}}}>
          <SectionsList selectedSectionName={selectedSectionName} 
            setSelectedSectionName={setSelectedSectionName}
            selectedSubsectionName={getSubsectionPanel}
            setSelectedSubsectionName={setSelectedSubsectionName}/>
          <Box sx={{flexGrow: 1, padding: 4}}>
            {getPanel()}
          </Box>
        </Box>
      </Box>
    </>
  )
}

export default EditUserPage